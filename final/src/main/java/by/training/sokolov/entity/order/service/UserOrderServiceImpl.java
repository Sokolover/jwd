package by.training.sokolov.order.service;

import by.training.sokolov.order.dao.UserOrderDao;
import by.training.sokolov.order.model.UserOrder;
import by.training.sokolov.service.GenericServiceImpl;

import java.sql.SQLException;

public class UserOrderServiceImpl extends GenericServiceImpl<UserOrder> implements UserOrderService {

    private UserOrderDao userOrderDao;

    public UserOrderServiceImpl(UserOrderDao dao) {
        super(dao);
        this.userOrderDao = dao;
    }

    @Override
    public UserOrder findInProgressUserOrder(Long id) throws SQLException {
        return userOrderDao.findInProgressUserOrder(id);
    }
}
