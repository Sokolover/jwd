package by.training.sokolov.jdbc;

import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.dao.UserDaoImplTest;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;

public class BlobInsertTest {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImplTest.class.getName());
    private static DishService dishService;

    @BeforeAll
    static void initValidator() {

        ApplicationContext.initialize();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        dishService = applicationContext.getBean(DishService.class);
    }

    @Test
    public void insertBlobInDish() throws SQLException, IOException, ConnectionException {

        Dish dish = new Dish();
        dish.setId(2L);
        dish.setName("burger1");
        dish.setCost(new BigDecimal(5));

        File file = new File("D://pic2.jpg");
        byte[] fileContent = Files.readAllBytes(file.toPath());
        String stringPicture = Base64.getEncoder().encodeToString(fileContent);
        dish.setPicture(stringPicture);

        dish.setDescription("yummy4");
        dish.getDishCategory().setId(2L);

        System.out.println(dish.toString());

        dishService.update(dish);
    }
}
