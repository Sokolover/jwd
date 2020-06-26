package by.training.sokolov.jdbc;

import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.dish.service.DishService;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;

public class BlobInsertTest {

    @Test
    public void insertBlobInDish() throws SQLException, IOException {
        //Inserting values

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

        DishService dishService = BeanFactory.getDishService();
        dishService.update(dish);
    }
}
