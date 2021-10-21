package by.training.sokolov.entity.order.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.user.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface UserOrderService extends GenericService<UserOrder> {

    UserOrder createNewOrder(User user) throws SQLException, ConnectionException;

    BigDecimal getOrderCost(UserOrder order) throws ConnectionException, SQLException;

    UserOrder getBuildingUpUserOrder(String sessionId) throws SQLException, ConnectionException;
}
