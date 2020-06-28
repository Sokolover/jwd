package by.training.sokolov.orderitem.service;

import by.training.sokolov.orderitem.dao.OrderItemDao;
import by.training.sokolov.orderitem.model.OrderItem;
import by.training.sokolov.service.GenericServiceImpl;

public class OrderItemServiceImpl extends GenericServiceImpl<OrderItem> implements OrderItemService {

    private OrderItemDao orderItemDao;

    public OrderItemServiceImpl(OrderItemDao dao) {
        super(dao);
        this.orderItemDao = dao;
    }

}
