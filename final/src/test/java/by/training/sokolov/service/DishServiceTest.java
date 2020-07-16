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

    @Test
    public void shouldUpdateDishPictureWithoutExceptions() throws SQLException, IOException, ConnectionException {

        long dishId = 2L;
        Dish dish = dishService.getById(dishId);

        File file = new File("D://pic2.jpg");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String stringPicture = Base64.getEncoder().encodeToString(fileContent);
        dish.setPicture(stringPicture);

        LOGGER.info(dish.toString());

        dishService.update(dish);

        LOGGER.info(dishService.getById(dishId));
    }
}
