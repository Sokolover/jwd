package by.training.sokolov.entity.order.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.order.model.UserOrder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserOrderDaoImpl extends GenericDao<UserOrder> implements UserOrderDao {

    private static final Logger LOGGER = Logger.getLogger(UserOrderDaoImpl.class.getName());
    private static final String TABLE_NAME = "user_order";
    private static final String SELECT_IN_PROGRESS_ORDER_BY_USER_ID_QUERY = "SELECT *\n" +
            "FROM {0}\n" +
            "WHERE user_account_id = ?\n" +
            "AND {0}.in_progress = 1";
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
                userOrder.setTimeOfDelivery(resultSet.getTimestamp("time_of_delivery"));
                userOrder.setOrderStatus(resultSet.getString("order_status"));
                userOrder.setInProgress(resultSet.getBoolean("in_progress"));
                userOrder.getUser().setId(resultSet.getLong("user_account_id"));
                userOrder.getDeliveryAddress().setId(resultSet.getLong("delivery_address_id"));
                return userOrder;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("time_of_delivery",
                        "order_status",
                        "in_progress",
                        "user_account_id",
                        "delivery_address_id");
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserOrder entity) throws SQLException {

                statement.setTimestamp(1, entity.getTimeOfDelivery());
                statement.setString(2, entity.getOrderStatus());
                statement.setBoolean(3, entity.getInProgress());
                statement.setLong(4, entity.getUser().getId());
                statement.setLong(5, entity.getDeliveryAddress().getId());
            }
        };
    }

    @Override
    public UserOrder findInProgressUserOrder(Long id) throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info("findInProgressUserOrder(Long id)--" + id);
        AtomicReference<UserOrder> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_IN_PROGRESS_ORDER_BY_USER_ID_QUERY, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(getUserOrderRowMapper().map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }
}