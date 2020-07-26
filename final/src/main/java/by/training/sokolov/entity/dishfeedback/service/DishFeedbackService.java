package by.training.sokolov.entity.dishfeedback.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;

import java.sql.SQLException;

public interface DishFeedbackService extends GenericService<DishFeedback> {

    DishFeedback getByUserIdAndDishId(Long userId, Long dishId) throws ConnectionException, SQLException;
}
