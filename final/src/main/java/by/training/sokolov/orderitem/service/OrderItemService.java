package by.training.sokolov.orderitem.service;

import by.training.sokolov.orderitem.model.OrderItem;
import by.training.sokolov.service.GenericService;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemService extends GenericService<OrderItem> {

    List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException;
}
