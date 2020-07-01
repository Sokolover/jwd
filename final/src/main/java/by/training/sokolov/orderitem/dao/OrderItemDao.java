package by.training.sokolov.orderitem.dao;

import by.training.sokolov.dao.CrudDao;
import by.training.sokolov.orderitem.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends CrudDao<OrderItem> {

    List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException;
}
