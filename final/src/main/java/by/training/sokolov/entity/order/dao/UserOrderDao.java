package by.training.sokolov.order.dao;

import by.training.sokolov.dao.CrudDao;
import by.training.sokolov.order.model.UserOrder;

import java.sql.SQLException;

public interface UserOrderDao extends CrudDao<UserOrder> {

    UserOrder findInProgressUserOrder(Long id) throws SQLException;
}
