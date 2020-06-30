package by.training.sokolov.orderitem.dao;

import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.dish.dao.DishDao;
import by.training.sokolov.dish.dao.DishDaoImpl;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.orderitem.model.OrderItem;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class OrderItemDaoImpl extends GenericDao<OrderItem> implements OrderItemDao {

    private static final String TABLE_NAME = "order_item";

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
        DishDao dishDao = new DishDaoImpl();
        for (OrderItem orderItem : orderItems) {
            Long itemDishId = orderItem.getDish().getId();
            Dish dish = dishDao.getById(itemDishId);
            orderItem.setDish(dish);
        }
        return orderItems;
    }

    @Override
    public OrderItem getById(Long id) throws SQLException {

        OrderItem orderItem = super.getById(id);
        Long itemDishId = orderItem.getDish().getId();
        DishDao dishDao = new DishDaoImpl();
        Dish dish = dishDao.getById(itemDishId);
        orderItem.setDish(dish);
        return orderItem;
    }
}
