package by.training.sokolov.entity.orderitem.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderItemDaoImpl extends GenericDao<OrderItem> implements OrderItemDao {

    private static final Logger LOGGER = Logger.getLogger(OrderItemDaoImpl.class.getName());
    private static final String TABLE_NAME = "order_item";
    private static final String SELECT_BY_DISH_ID_AND_USER_ORDER_ID = "" +
            "SELECT {0}.*\n" +
            "FROM {0}\n" +
            "WHERE {0}.dish_id = ?\n" +
            "  AND {0}.user_order_id = ?";
    private static final String SELECT_BY_DISH_CATEGORY_NAME_QUERY = "" +
            "SELECT {0}.*\n" +
            "FROM {0},\n" +
            "     dish,\n" +
            "     dish_category\n" +
            "WHERE {0}.dish_id = dish.id\n" +
            "  AND dish.dish_category_id = dish_category.id\n" +
            "  AND dish_category.category_name = ?";
    private static final String SELECT_ALL_BY_ORDER_ID_QUERY = "" +
            "SELECT {0}.*\n" +
            "FROM {0},\n" +
            "     user_order\n" +
            "WHERE {0}.user_order_id = user_order.id\n" +
            "  AND user_order.id = ?";
    private final Lock connectionLock = new ReentrantLock();
    private final ConnectionManager connectionManager;

    public OrderItemDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getOrderItemRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<OrderItem> getOrderItemRowMapper() {

        return new IdentifiedRowMapper<OrderItem>() {

            @Override
            public OrderItem map(ResultSet resultSet) throws SQLException, IOException {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(resultSet.getLong("id"));
                orderItem.setDishAmount(resultSet.getInt("dish_amount"));
                orderItem.setItemCost(resultSet.getBigDecimal("item_cost"));
                orderItem.getDish().setId(resultSet.getLong("dish_id"));
                orderItem.getUserOrder().setId(resultSet.getLong("user_order_id"));
                return orderItem;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("dish_amount",
                        "item_cost",
                        "dish_id",
                        "user_order_id");
            }

            @Override
            public void populateStatement(PreparedStatement statement, OrderItem entity) throws SQLException {

                statement.setInt(1, entity.getDishAmount());
                statement.setBigDecimal(2, entity.getItemCost());
                statement.setLong(3, entity.getDish().getId());
                statement.setLong(4, entity.getUserOrder().getId());
            }
        };
    }

    @Override
    public List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info("findAllItemsByOrderId()");
        List<OrderItem> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_ALL_BY_ORDER_ID_QUERY, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, orderId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    try {
                        result.add(getOrderItemRowMapper().map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            return result;
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public OrderItem getFromCurrentOrderByDishId(Long dishId, Long userOrderId) throws ConnectionException, SQLException {

        connectionLock.lock();
        LOGGER.info("getById()--" + dishId);
        AtomicReference<OrderItem> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_BY_DISH_ID_AND_USER_ORDER_ID, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, dishId);
                statement.setLong(2, userOrderId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(getOrderItemRowMapper().map(resultSet));
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

    @Override
    public OrderItem getByDishCategoryName(String categoryName) throws ConnectionException, SQLException {

        connectionLock.lock();
        LOGGER.info("getByDishCategoryName(String categoryName)");
        AtomicReference<OrderItem> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_BY_DISH_CATEGORY_NAME_QUERY, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, "'" + categoryName + "'");
                statement.setString(1, categoryName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(getOrderItemRowMapper().map(resultSet));
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
