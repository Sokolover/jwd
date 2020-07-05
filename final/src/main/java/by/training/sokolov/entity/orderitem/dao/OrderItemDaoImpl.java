package by.training.sokolov.orderitem.dao;

import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.dish.dao.DishDao;
import by.training.sokolov.dish.dao.DishDaoImpl;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.orderitem.model.OrderItem;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderItemDaoImpl extends GenericDao<OrderItem> implements OrderItemDao {

    private static final Logger LOGGER = Logger.getLogger(OrderItemDaoImpl.class.getName());
    private static final String TABLE_NAME = "order_item";
    private final Lock connectionLock = new ReentrantLock();

    private static final String FIND_ALL_ITEMS_BY_ORDER_ID_QUERY =
            "SELECT {0}.*\n" +
                    "FROM {0}, user_order\n" +
                    "WHERE {0}.user_order_id = user_order_id\n" +
                    "AND {0}.user_order_id = ?";

    public OrderItemDaoImpl() {
        super(TABLE_NAME, getOrderItemRowMapper());
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
    public List<OrderItem> findAll() throws SQLException {

        List<OrderItem> orderItems = super.findAll();
        findOrderItemsDish(orderItems);
        return orderItems;
    }

    private void findOrderItemsDish(List<OrderItem> orderItems) throws SQLException {
        DishDao dishDao = new DishDaoImpl();
        for (OrderItem orderItem : orderItems) {
            Long itemDishId = orderItem.getDish().getId();
            Dish dish = dishDao.getById(itemDishId);
            orderItem.setDish(dish);
        }
    }

    @Override
    public OrderItem getById(Long id) throws SQLException {

        OrderItem orderItem = super.getById(id);

        DishDao dishDao = new DishDaoImpl();
        Long itemDishId = orderItem.getDish().getId();
        Dish dish = dishDao.getById(itemDishId);
        orderItem.setDish(dish);

        return orderItem;
    }

    public List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException {

        connectionLock.lock();
        LOGGER.info("findAllItemsByOrderId()");
        List<OrderItem> result = new ArrayList<>();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            String sql = MessageFormat.format(FIND_ALL_ITEMS_BY_ORDER_ID_QUERY, TABLE_NAME);
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
            findOrderItemsDish(result);
            return result;
        } finally {
            connectionLock.unlock();
        }
    }

}
