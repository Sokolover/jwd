package by.training.sokolov.entity.dish.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;

import java.sql.SQLException;
import java.util.List;

public interface DishService extends GenericService<Dish> {

    List<Dish> getByCategory(String categoryName) throws ConnectionException, SQLException;
}
