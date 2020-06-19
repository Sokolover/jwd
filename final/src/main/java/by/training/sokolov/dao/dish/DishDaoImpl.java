package by.training.sokolov.dao.dish;

import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.model.Dish;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DishDaoImpl extends GenericDao<Dish> implements DishDao {

    private static final String TABLE_NAME = "dish";

    public DishDaoImpl() {
        super(TABLE_NAME, getDishRowMapper());
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static IdentifiedRowMapper<Dish> getDishRowMapper() {

        return new IdentifiedRowMapper<Dish>() {

            @Override
            public Dish map(ResultSet resultSet) throws SQLException {
                Dish dish = new Dish();
                dish.setId(resultSet.getLong("id"));
                dish.setName(resultSet.getString("dish_name"));
                dish.setCost(resultSet.getBigDecimal("dish_cost"));
                dish.setDescription(resultSet.getString("dish_description"));
                dish.setCategoryId(resultSet.getLong("dish_category_id"));
                return dish;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("dish_name",
                        "dish_cost",
                        "dish_description",
                        "dish_picture",
                        "dish_category_id");
            }

            @Override
            public void populateStatement(PreparedStatement statement, Dish entity) throws SQLException {

                statement.setString(1, entity.getName());
                statement.setBigDecimal(2, entity.getCost());
                statement.setString(3, entity.getDescription());
                statement.setLong(4, 111L);
                statement.setLong(5, entity.getCategoryId());
            }
        };
    }

}
