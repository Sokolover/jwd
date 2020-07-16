package by.training.sokolov.dao;

import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.dao.UserDao;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoImplTest {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImplTest.class.getName());
    private static UserDao userDao;

    @BeforeAll
    public static void initContext() {

        ApplicationContext.initialize();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        userDao = applicationContext.getBean(UserDao.class);
    }

    @Test
    public void shouldFindAllUsers() throws ConnectionException, SQLException {

        List<User> all = userDao.findAll();
        for (User user : all) {
            LOGGER.info(user.toString());
        }
        assertTrue(all.size() > 0);
    }

    @Test
    public void shouldGetUserByName() throws ConnectionException, SQLException {

        String name = "qwerty";
        User user = userDao.getByName(name);
        String message = "account got by name = " + name + ", account: " + user.toString();
        LOGGER.info(message);
        assertNotNull(user);
    }
}
