package by.training.sokolov.dish.dao;

import by.training.sokolov.category.dao.DishCategoryDao;
import by.training.sokolov.category.dao.DishCategoryDaoImpl;
import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.dish.model.Dish;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DishDaoImpl extends GenericDao<Dish> implements DishDao {

    private static final String TABLE_NAME = "dish";

    public DishDaoImpl() {
        super(TABLE_NAME, getDishRowMapper());
    }

    private static IdentifiedRowMapper<Dish> getDishRowMapper() {

        return new IdentifiedRowMapper<Dish>() {

            @Override
            public Dish map(ResultSet resultSet) throws SQLException, IOException {
                Dish dish = new Dish();
                dish.setId(resultSet.getLong("id"));
                dish.setName(resultSet.getString("dish_name"));
                dish.setCost(resultSet.getBigDecimal("dish_cost"));
                dish.setDescription(resultSet.getString("dish_description"));
                dish.setPicture(convertBlobToString(resultSet));
                dish.getDishCategory().setId(resultSet.getLong("dish_category_id"));
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
                statement.setBlob(4, convertStringToBlob(entity.getPicture()));
                statement.setLong(5, entity.getDishCategory().getId());
            }
        };
    }

    private static Blob convertStringToBlob(String stringPicture) throws SQLException {
        byte[] byteData = stringPicture.getBytes(StandardCharsets.UTF_8);
        Connection connection = BasicConnectionPool.getInstance().getConnection();
        Blob blobPicture = connection.createBlob();
        blobPicture.setBytes(1, byteData);
        return blobPicture;
    }

    private static String convertBlobToString(ResultSet resultSet) throws SQLException, IOException {
        Blob blob = resultSet.getBlob("dish_picture");

        InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

//        byte[] imageBytes = outputStream.toByteArray();
//        String stringPicture = Base64.getEncoder().encodeToString(imageBytes);

        String stringPicture = outputStream.toString();

        inputStream.close();
        outputStream.close();

        return stringPicture;
    }

    @Override
    public List<Dish> findAll() throws SQLException {

        List<Dish> dishes = super.findAll();
        DishCategoryDao dishCategoryDao = new DishCategoryDaoImpl();
        for (Dish dish : dishes) {
            Long categoryId = dish.getDishCategory().getId();
            DishCategory dishCategory = dishCategoryDao.getById(categoryId);
            dish.setDishCategory(dishCategory);
        }
        return dishes;
    }

    @Override
    public Dish getById(Long id) throws SQLException {

        Dish dish = super.getById(id);
        DishCategoryDao dishCategoryDao = new DishCategoryDaoImpl();
        DishCategory dishCategory = dishCategoryDao.getById(dish.getDishCategory().getId());
        dish.setDishCategory(dishCategory);
        return dish;
    }
}
