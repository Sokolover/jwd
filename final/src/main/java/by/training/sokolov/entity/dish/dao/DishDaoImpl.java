package by.training.sokolov.entity.dish.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.dish.model.Dish;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DishDaoImpl extends GenericDao<Dish> implements DishDao {

    private static final Logger LOGGER = Logger.getLogger(DishDaoImpl.class.getName());
    private static final String TABLE_NAME = "dish";
    private static final String SELECT_BY_DISH_CATEGORY_QUERY = "SELECT {0}.*\n" +
            "FROM {0},\n" +
            "     dish_category\n" +
            "WHERE {0}.dish_category_id = dish_category.id\n" +
            "AND dish_category.category_name = ?";
    private static ConnectionManager connectionManager;
    private final Lock connectionLock = new ReentrantLock();

    public DishDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getDishRowMapper(), connectionManager);
        DishDaoImpl.connectionManager = connectionManager;
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
            public void populateStatement(PreparedStatement statement, Dish entity) throws SQLException, ConnectionException {

                statement.setString(1, entity.getName());
                statement.setBigDecimal(2, entity.getCost());
                statement.setString(3, entity.getDescription());
                statement.setBlob(4, convertStringToBlob(entity.getPicture()));
                statement.setLong(5, entity.getDishCategory().getId());
            }
        };
    }

    private static Blob convertStringToBlob(String stringPicture) throws SQLException, ConnectionException {

        byte[] byteData = stringPicture.getBytes(StandardCharsets.UTF_8);
        Connection connection = connectionManager.getConnection();
        Blob blobPicture = connection.createBlob();
        blobPicture.setBytes(1, byteData);
        return blobPicture;
    }

    private static String convertBlobToString(ResultSet resultSet) throws SQLException, IOException {

        Blob blob = resultSet.getBlob("dish_picture");
        InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        String stringPicture = outputStream.toString();

        inputStream.close();
        outputStream.close();

        return stringPicture;
    }

    @Override
    public List<Dish> getByCategory(String categoryName) throws ConnectionException, SQLException {

        connectionLock.lock();
        LOGGER.info("findAll()");
        List<Dish> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_BY_DISH_CATEGORY_QUERY, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, "'" + categoryName + "'");
                statement.setString(1, categoryName);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    try {
                        result.add(getDishRowMapper().map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            return result;
        } finally {
            connectionLock.unlock();
        }
    }
}
