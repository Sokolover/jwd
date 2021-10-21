package by.training.sokolov.entity.category.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;

import java.sql.SQLException;

public interface DishCategoryService extends GenericService<DishCategory> {

    void deleteByName(String name) throws SQLException, ConnectionException;
}
