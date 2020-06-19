package by.training.sokolov.jdbc;

import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.db.ConnectionPool;
import by.training.sokolov.model.Dish;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class JdbcTest {

    private static final Logger LOGGER = Logger.getLogger(JdbcTest.class);
    private ConnectionPool connectionPool;

    @Before
    public void initializeConnectionPool() {
        connectionPool = BasicConnectionPool.getInstance();
    }

    @Test
    public void readFromDishTable() throws SQLException {

        Long id;
        String name;
        BigDecimal cost;
        String description;
        Long categoryId;

        List<Dish> dishes = new ArrayList<>();

        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM dish");

        while (resultSet.next()) {
            id = resultSet.getLong("id");
            name = resultSet.getString("dish_name");
            cost = resultSet.getBigDecimal("dish_cost");
            description = resultSet.getString("dish_description");
            categoryId = resultSet.getLong("dish_category_id");

            Dish dish = new Dish(id, name, cost, description, categoryId);
            dishes.add(dish);

            LOGGER.info("id: " + id + " name: " + name);
        }

        resultSet.close();
        statement.close();
        connectionPool.releaseConnection(connection);

        Assert.assertEquals(2, dishes.size());
    }

}
