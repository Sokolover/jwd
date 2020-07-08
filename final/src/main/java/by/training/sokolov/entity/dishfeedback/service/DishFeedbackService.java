package by.training.sokolov.entity.dishfeedback.service;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;
import by.training.sokolov.core.service.GenericService;

import java.sql.SQLException;

public interface DishFeedbackService extends GenericService<DishFeedback> {

    DishFeedback getUsersFeedbackByDishId(Long userId, Long dishId) throws ConnectionException, SQLException;
}
