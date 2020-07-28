package by.training.sokolov.validation;

import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.entity.dish.model.Dish;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class DishCostValidatorTest {

    private static final Logger LOGGER = Logger.getLogger(DishCostValidatorTest.class.getName());
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
        dish.setCost(new BigDecimal("-1.0"));

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
        dish.setCost(new BigDecimal("0.0"));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldNotFailValidationOnPositiveCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("1.0"));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldFailValidationOnMoreThan3IntegerDigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("1000.0"));

        ValidationResult result = beanValidator.validate(dish);

        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
    }

    @Test
    public void shouldFailValidationOnMoreThan2FractionDigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("0.001"));

        ValidationResult result = beanValidator.validate(dish);

        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
    }

    @Test
    public void shouldNotFailValidationOnLessThan3IntegerDigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("10.0"));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldNotFailValidationOnLessThan2FractionDigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("1.1"));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldNotFailValidationOn3IntegerDigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("100.0"));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldNotFailValidationOn2FractionDigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("1.01"));

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldFailValidationOnNameField() {

        Dish dish = new Dish();
        dish.setName("sou");

        ValidationResult result = beanValidator.validate(dish);

        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
    }

    @Test
    public void shouldNotFailValidationOnNameField() {

        Dish dish = new Dish();
        dish.setName("soup");

        ValidationResult result = beanValidator.validate(dish);
        Assert.assertEquals(0, result.getBrokenFields().size());
    }

    @Test
    public void shouldFailValidationOnMoreThanOnlyInteger3DigitsCost() {

        Dish dish = new Dish();
        dish.setCost(new BigDecimal("1000"));

        ValidationResult result = beanValidator.validate(dish);

        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
    }

//    @Test
//    public void shouldFailValidationOn() {
//
//        Dish dish = new Dish();
//        dish.setCost(new BigDecimal(".01"));
//
//        ValidationResult result = beanValidator.validate(dish);
//
//        Assert.assertNotNull(result);
//        List<BrokenField> brokenFields = result.getBrokenFields();
//        Assert.assertEquals(1, brokenFields.size());
//
//        BrokenField brokenField = brokenFields.get(0);
//        LOGGER.info("Broken field is " + brokenField.getFieldName());
//    }
}
