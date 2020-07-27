package by.training.sokolov.entity.order.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.order.constants.OrderStatus;
import by.training.sokolov.entity.order.model.UserOrder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_AND_GOT_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_FOR_ENTITY_ID_MESSAGE;
import static by.training.sokolov.entity.order.dao.UserOrderTableConstants.*;
import static java.lang.String.format;

public class UserOrderDaoImpl extends GenericDao<UserOrder> implements UserOrderDao {

    private static final Logger LOGGER = Logger.getLogger(UserOrderDaoImpl.class.getName());

    private static final String SELECT_BUILDING_UP_ORDER_BY_USER_ID_QUERY = "" +
            "SELECT *\n" +
            "FROM {0}\n" +
            "WHERE {0}.{1} = ?\n" +
            "  AND {0}.{2} LIKE ?";
    private final Lock connectionLock = new ReentrantLock();
    private final ConnectionManager connectionManager;

    public UserOrderDaoImpl(ConnectionManager connectionManager) {
        super(USER_ORDER_TABLE_NAME, getUserOrderRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<UserOrder> getUserOrderRowMapper() {

        return new IdentifiedRowMapper<UserOrder>() {

            @Override
            public UserOrder map(ResultSet resultSet) throws SQLException {
                UserOrder userOrder = new UserOrder();
                userOrder.setId(resultSet.getLong(ID));
                LocalDateTime localDateTime = resultSet.getObject(TIME_OF_DELIVERY, LocalDateTime.class);
                userOrder.setTimeOfDelivery(localDateTime);
                userOrder.setOrderStatus(OrderStatus.fromString(resultSet.getString(ORDER_STATUS)));
                userOrder.setUserId(resultSet.getLong(USER_ACCOUNT_ID));
                userOrder.getDeliveryAddress().setId(resultSet.getLong(DELIVERY_ADDRESS_ID));
                userOrder.setCustomerName(resultSet.getString(CUSTOMER_NAME));
                userOrder.setCustomerPhoneNumber(resultSet.getString(CUSTOMER_PHONE_NUMBER));
                return userOrder;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList(TIME_OF_DELIVERY,
                        ORDER_STATUS,
                        USER_ACCOUNT_ID,
                        DELIVERY_ADDRESS_ID,
                        CUSTOMER_NAME,
                        CUSTOMER_PHONE_NUMBER);
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserOrder entity) throws SQLException {

                statement.setObject(1, entity.getTimeOfDelivery());
                statement.setString(2, entity.getOrderStatus().getValue());
                statement.setLong(3, entity.getUserId());
                statement.setLong(4, entity.getDeliveryAddress().getId());
                statement.setString(5, entity.getCustomerName());
                statement.setString(6, entity.getCustomerPhoneNumber());
            }
        };
    }

    @Override
    public UserOrder findBuildingUpUserOrder(Long id) throws SQLException, ConnectionException {

        connectionLock.lock();

        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_ID_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, id));

        AtomicReference<UserOrder> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_BUILDING_UP_ORDER_BY_USER_ID_QUERY, USER_ORDER_TABLE_NAME, USER_ACCOUNT_ID, ORDER_STATUS);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.setString(2, OrderStatus.BUILD_UP.getValue());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(getUserOrderRowMapper().map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                        return null;
                    }
                } else {
                    LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, result.get()));
                    return null;
                }
            }
            LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, result.get().toString()));
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }
}
