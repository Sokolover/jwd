package by.training.sokolov.entity.order.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;

import java.sql.SQLException;

public interface UserOrderDao extends CrudDao<UserOrder> {

    UserOrder findBuildingUpUserOrder(Long id) throws SQLException, ConnectionException;
}
