package by.training.sokolov.validation;

import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.dao.UserDaoImplTest;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

public class ValidatorTest {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImplTest.class.getName());
    private static BeanValidator beanValidator;

    @BeforeAll
    static void initValidator() {

        ApplicationContext.initialize();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        beanValidator = applicationContext.getBean(BeanValidator.class);
    }

    @Test
    public void shouldFailValidationOnEmailField() {

        User user = new User();
        user.setName("Ivanov Ivan");
        user.setEmail("@inavov.com");
        user.setPassword("Ivanov1234");

        ValidationResult result = beanValidator.validate(user);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
        Assert.assertEquals("email", brokenField.getViolatedRule());
        Assert.assertEquals("@inavov.com", brokenField.getFieldValue());
        Assert.assertEquals("email", brokenField.getFieldName());
    }

    @Test
    public void shouldFailValidationOnNameField() {

        User user = new User();
        user.setName("Ivan");
        user.setEmail("Ivanov@gmail.com");
        user.setPassword("Ivanov1234");

        ValidationResult result = beanValidator.validate(user);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
        Assert.assertEquals("minLength", brokenField.getViolatedRule());
        Assert.assertEquals("Ivan", brokenField.getFieldValue());
        Assert.assertEquals("name", brokenField.getFieldName());
    }

    @Test
    public void shouldFailValidationOnPasswordField() {

        User user = new User();
        user.setName("Ivanov Ivan");
        user.setEmail("Ivanov@gmail.com");
        user.setPassword("ivanov1234");

        ValidationResult result = beanValidator.validate(user);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
        Assert.assertEquals("password", brokenField.getViolatedRule());
        Assert.assertEquals("ivanov1234", brokenField.getFieldValue());
        Assert.assertEquals("password", brokenField.getFieldName());
    }

    @Test
    public void shouldFailValidationOnPhoneNumberField() {

        User user = new User();
        user.setName("Ivanov Ivan");
        user.setEmail("Ivanov@gmail.com");
        user.setPassword("Ivanov1234");
        user.setPhoneNumber("+37529123456");

        ValidationResult result = beanValidator.validate(user);
        Assert.assertNotNull(result);
        List<BrokenField> brokenFields = result.getBrokenFields();
        Assert.assertEquals(1, brokenFields.size());

        BrokenField brokenField = brokenFields.get(0);
        LOGGER.info("Broken field is " + brokenField.getFieldName());
        Assert.assertEquals("phoneNumber", brokenField.getViolatedRule());
        Assert.assertEquals("+37529123456", brokenField.getFieldValue());
        Assert.assertEquals("phoneNumber", brokenField.getFieldName());
    }
}
