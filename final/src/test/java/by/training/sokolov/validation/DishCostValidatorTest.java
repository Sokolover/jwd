package by.training.sokolov.validation;

import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.entity.dish.model.Dish;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class DishValidatorTest {

    private static final Logger LOGGER = Logger.getLogger(DishValidatorTest.class.getName());
    private static BeanValidator beanValidator;

    @BeforeAll
    static void initValidator() {

        ApplicationContext.initialize();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        beanValidator = applicationContext.getBean(BeanValidator.class);
    }

    @Test
    public void shouldFailValidationOnNegativeCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal(-1));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
    }

    @Test
    public void shouldNotFailValidationOnZeroCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal(0.0));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldNotFailValidationOnPositiveCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal(1.0));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }
}
