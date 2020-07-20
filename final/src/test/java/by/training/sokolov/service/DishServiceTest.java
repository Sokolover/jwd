package by.training.sokolov.service;

import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;

public class DishServiceTest {

    private static final Logger LOGGER = Logger.getLogger(DishServiceTest.class.getName());
    private static DishService dishService;

    @BeforeAll
    static void initValidator() {

        ApplicationContext.initialize();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        dishService = applicationContext.getBean(DishService.class);
    }

    /*
    todo сделать этот тест с инмемори базой
     */

    @Test
    public void shouldUpdateDishPictureWithoutExceptions() throws SQLException, IOException, ConnectionException {

        long dishId = 1L;
        String dishName = "Pizza pepperoni";
        File file = new File("D://pizza_pepperoni.jpg");

//        long dishId = 2L;
//        String dishName = "Cheeseburger with bacon";
//        File file = new File("D://burger_bacon_cheese.jpg");

//        long dishId = 18L;
//        String dishName = "Soup kharcho";
//        File file = new File("D://soup_kharcho.jpg");

        byte[] fileContent = Files.readAllBytes(file.toPath());
        String stringPicture = Base64.getEncoder().encodeToString(fileContent);

        Dish dish = dishService.getById(dishId);
        dish.setPicture(stringPicture);
        dish.setName(dishName);

        LOGGER.info(dish.toString());

        dishService.update(dish);

        LOGGER.info(dishService.getById(dishId));
    }
}
