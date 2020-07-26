package by.training.sokolov.entity.order.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
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
import static java.lang.String.format;

public class UserOrderDaoImpl extends GenericDao<UserOrder> implements UserOrderDao {

    private static final Logger LOGGER = Logger.getLogger(UserOrderDaoImpl.class.getName());

    private static final String TABLE_NAME = "user_order";
    private static final String SELECT_BUILDING_UP_ORDER_BY_USER_ID_QUERY = "" +
            "SELECT *\n" +
            "FROM {0}\n" +
            "WHERE user_account_id = ?\n" +
            "  AND {0}.order_status LIKE ?";
    private final Lock connectionLock = new ReentrantLock();
    private final ConnectionManager connectionManager;

    public UserOrderDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getUserOrderRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<UserOrder> getUserOrderRowMapper() {

        return new IdentifiedRowMapper<UserOrder>() {

            @Override
            public UserOrder map(ResultSet resultSet) throws SQLException {
                UserOrder userOrder = new UserOrder();
                userOrder.setId(resultSet.getLong("id"));
                LocalDateTime localDateTime = resultSet.getObject("time_of_delivery", LocalDateTime.class);
                userOrder.setTimeOfDelivery(localDateTime);
                userOrder.setOrderStatus(OrderStatus.fromString(resultSet.getString("order_status")));
                userOrder.setUserId(resultSet.getLong("user_account_id"));
                userOrder.getDeliveryAddress().setId(resultSet.getLong("delivery_address_id"));
                userOrder.setCustomerName(resultSet.getString("customer_name"));
                userOrder.setCustomerPhoneNumber(resultSet.getString("customer_phone_number"));
                return userOrder;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("time_of_delivery",
                        "order_status",
                        "user_account_id",
                        "delivery_address_id",
                        "customer_name",
                        "customer_phone_number");
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
            String sql = MessageFormat.format(SELECT_BUILDING_UP_ORDER_BY_USER_ID_QUERY, TABLE_NAME);
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
                }
            }
            LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, result.get().toString()));
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }
}
