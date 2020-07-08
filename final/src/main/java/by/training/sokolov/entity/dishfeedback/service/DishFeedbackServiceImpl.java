package by.training.sokolov.entity.dishfeedback.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dishfeedback.dao.DishFeedbackDao;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;

import java.sql.SQLException;

public class DishFeedbackServiceImpl extends GenericServiceImpl<DishFeedback> implements DishFeedbackService {

    private DishFeedbackDao dishFeedbackDao;

    public DishFeedbackServiceImpl(DishFeedbackDao dao) {
        super(dao);
        this.dishFeedbackDao = dao;
    }

    @Override
    public DishFeedback getUsersFeedbackByDishId(Long userId, Long dishId) throws ConnectionException, SQLException {

        return dishFeedbackDao.getUsersFeedbackByDishId(userId, dishId);
    }

    @Override
    public void update(DishFeedback entity) throws SQLException, ConnectionException {

        super.update(entity);
    }

    @Override
    public Long save(DishFeedback entity) throws SQLException, ConnectionException {

        return super.save(entity);
    }
}
