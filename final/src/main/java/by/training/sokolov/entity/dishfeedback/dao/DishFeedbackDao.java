package by.training.sokolov.entity.dishfeedback.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;

import java.sql.SQLException;

public interface DishFeedbackDao extends CrudDao<DishFeedback> {

    DishFeedback getByUserIdAndDishId(Long userId, Long dishId) throws ConnectionException, SQLException;
}
