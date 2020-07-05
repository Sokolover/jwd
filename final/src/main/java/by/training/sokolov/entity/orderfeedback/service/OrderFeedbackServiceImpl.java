package by.training.sokolov.orderfeedback.service;

import by.training.sokolov.orderfeedback.dao.OrderFeedbackDao;
import by.training.sokolov.orderfeedback.model.OrderFeedback;
import by.training.sokolov.service.GenericServiceImpl;

public class OrderFeedbackServiceImpl extends GenericServiceImpl<OrderFeedback> implements OrderFeedbackService {

    private OrderFeedbackDao orderFeedbackDao;

    public OrderFeedbackServiceImpl(OrderFeedbackDao dao) {
        super(dao);
        this.orderFeedbackDao = dao;
    }
}
