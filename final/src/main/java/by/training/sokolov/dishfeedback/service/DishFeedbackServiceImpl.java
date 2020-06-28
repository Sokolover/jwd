package by.training.sokolov.dishfeedback.service;

import by.training.sokolov.dishfeedback.dao.DishFeedbackDao;
import by.training.sokolov.dishfeedback.model.DishFeedback;
import by.training.sokolov.service.GenericServiceImpl;

public class DishFeedbackServiceImpl extends GenericServiceImpl<DishFeedback> implements DishFeedbackDao {

    private DishFeedbackDao dishFeedbackDao;

    public DishFeedbackServiceImpl(DishFeedbackDao dao) {
        super(dao);
        this.dishFeedbackDao = dao;
    }
}
