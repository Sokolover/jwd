package by.training.sokolov.entity.orderitem.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.orderitem.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends CrudDao<OrderItem> {

    List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException, ConnectionException;

    OrderItem getFromCurrentOrderByDishId(Long dishId, Long userOrderId) throws ConnectionException, SQLException;

    OrderItem getFromCurrentOrderByDishCategoryName(String categoryName, Long userOrderId) throws ConnectionException, SQLException;
}
