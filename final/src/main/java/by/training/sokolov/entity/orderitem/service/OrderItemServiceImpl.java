package by.training.sokolov.entity.orderitem.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.Transactional;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.entity.orderitem.dao.OrderItemDao;
import by.training.sokolov.entity.orderitem.model.OrderItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OrderItemServiceImpl extends GenericServiceImpl<OrderItem> implements OrderItemService {

    private final OrderItemDao orderItemDao;
    private final DishService dishService;

    public OrderItemServiceImpl(OrderItemDao orderItemDao, DishService dishService) {
        super(orderItemDao);
        this.orderItemDao = orderItemDao;
        this.dishService = dishService;
    }

    @Transactional
    @Override
    public List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException, ConnectionException {

        List<OrderItem> orderItems = orderItemDao.findAllItemsByOrderId(orderId);
        findOrderItemsDish(orderItems);

        return orderItems;
    }

    private void findOrderItemsDish(List<OrderItem> orderItems) throws SQLException, ConnectionException {

        for (OrderItem orderItem : orderItems) {

            Long itemDishId = orderItem.getDish().getId();
            Dish dish = dishService.getById(itemDishId);
            orderItem.setDish(dish);
        }
    }

    @Override
    public void addNewOrderItem(OrderItem orderItem) throws SQLException, ConnectionException {

        Dish dish = orderItem.getDish();
        Integer dishAmount = orderItem.getDishAmount();
        BigDecimal itemCost = dish.getCost().multiply(BigDecimal.valueOf(dishAmount));
        orderItem.setItemCost(itemCost);

        this.save(orderItem);
    }

    @Override
    public OrderItem getFromCurrentOrderByDishId(Long dishId, Long userOrderId) throws ConnectionException, SQLException {

        return orderItemDao.getFromCurrentOrderByDishId(dishId, userOrderId);
    }

    @Transactional
    @Override
    public OrderItem getFromCurrentOrderByDishCategoryName(String categoryName, Long userOrderId) throws ConnectionException, SQLException {

        OrderItem orderItem = orderItemDao.getFromCurrentOrderByDishCategoryName(categoryName, userOrderId);
        if (Objects.isNull(orderItem)) {
            return null;
        }
        Long dishId = orderItem.getDish().getId();
        orderItem.setDish(dishService.getById(dishId));

        return orderItem;
    }

    @Override
    public List<OrderItem> findAll() throws SQLException, ConnectionException {

        return super.findAll();
    }

    @Override
    public void deleteById(Long id) throws SQLException, ConnectionException {

        super.deleteById(id);
    }

}
