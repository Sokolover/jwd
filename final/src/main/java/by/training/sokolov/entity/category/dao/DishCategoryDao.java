package by.training.sokolov.entity.category.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;

import java.sql.SQLException;

public interface DishCategoryDao extends CrudDao<DishCategory> {

    DishCategory getByName(String categoryName) throws SQLException, ConnectionException;
}
