package by.training.sokolov.entity.dish.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;

import java.sql.SQLException;
import java.util.List;

public interface DishDao extends CrudDao<Dish> {

    List<Dish> getByCategory(String categoryName) throws ConnectionException, SQLException;
}