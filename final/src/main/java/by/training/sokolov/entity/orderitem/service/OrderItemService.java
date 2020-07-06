package by.training.sokolov.entity.orderitem.service;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.core.service.GenericService;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemService extends GenericService<OrderItem> {

    List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException, ConnectionException;

    void addNewOrderItem(OrderItem orderItem) throws SQLException, ConnectionException;

    OrderItem getByDishId(Long id) throws ConnectionException, SQLException;

    OrderItem getByDishCategoryName(String categoryName) throws ConnectionException, SQLException;

}
