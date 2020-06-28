package by.training.sokolov.order.service;

import by.training.sokolov.order.model.UserOrder;
import by.training.sokolov.service.GenericService;

import java.sql.SQLException;

public interface UserOrderService extends GenericService<UserOrder> {

    UserOrder findInProgressUserOrder(Long id) throws SQLException;
}
