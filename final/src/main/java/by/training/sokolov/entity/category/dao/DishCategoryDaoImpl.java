package by.training.sokolov.category.dao;

import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DishCategoryDaoImpl extends GenericDao<DishCategory> implements DishCategoryDao {

    private final static Logger LOGGER = Logger.getLogger(DishCategoryDaoImpl.class.getName());
    private static final String TABLE_NAME = "dish_category";
    private final Lock connectionLock = new ReentrantLock();

    public DishCategoryDaoImpl() {
        super(TABLE_NAME, getDishCategoryRowMapper());
    }

    private static IdentifiedRowMapper<DishCategory> getDishCategoryRowMapper() {

        return new IdentifiedRowMapper<DishCategory>() {

            @Override
            public DishCategory map(ResultSet resultSet) throws SQLException {
                DishCategory dishCategory = new DishCategory();
                dishCategory.setId(resultSet.getLong("id"));
                dishCategory.setCategoryName(resultSet.getString("category_name"));
                return dishCategory;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList("category_name");
            }

            @Override
            public void populateStatement(PreparedStatement statement, DishCategory entity) throws SQLException {

                statement.setString(1, entity.getCategoryName());
            }
        };
    }
}
