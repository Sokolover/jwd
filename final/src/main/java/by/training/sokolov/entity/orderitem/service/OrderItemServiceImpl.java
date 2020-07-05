package by.training.sokolov.orderitem.service;

import by.training.sokolov.orderitem.dao.OrderItemDao;
import by.training.sokolov.orderitem.model.OrderItem;
import by.training.sokolov.service.GenericServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class OrderItemServiceImpl extends GenericServiceImpl<OrderItem> implements OrderItemService {

    private OrderItemDao orderItemDao;

    public OrderItemServiceImpl(OrderItemDao dao) {
        super(dao);
        this.orderItemDao = dao;
    }

    @Override
    public List<OrderItem> findAllItemsByOrderId(Long orderId) throws SQLException {

        return orderItemDao.findAllItemsByOrderId(orderId);
    }
}
