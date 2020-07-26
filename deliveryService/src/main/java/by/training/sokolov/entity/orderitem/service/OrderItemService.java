package by.training.sokolov.entity.orderitem.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.orderitem.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemService extends GenericService<OrderItem> {

    List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException, ConnectionException;

    void addNewOrderItem(OrderItem orderItem) throws SQLException, ConnectionException;

    OrderItem getFromCurrentOrderByDishId(Long dishId, Long userOrderId) throws ConnectionException, SQLException;

    List<OrderItem> getFromCurrentOrderByDishCategoryName(String categoryName, Long userOrderId) throws ConnectionException, SQLException;

    void deleteByOrderId(Long orderId) throws SQLException, ConnectionException;
}
