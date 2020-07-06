package by.training.sokolov.entity.orderitem.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.orderitem.model.OrderItem;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends CrudDao<OrderItem> {

    List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException, ConnectionException;

    OrderItem getByDishId(Long id) throws ConnectionException, SQLException;

    OrderItem getByDishCategoryName(String categoryName) throws ConnectionException, SQLException;
}
