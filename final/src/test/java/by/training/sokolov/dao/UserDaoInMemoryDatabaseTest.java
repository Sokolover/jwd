package by.training.sokolov.dao;

import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionPool;
import by.training.sokolov.db.ConnectionPoolImpl;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RunWith(JUnit4.class)
public class UserDaoInMemoryDatabaseTest {

    private static final Logger LOGGER = Logger.getLogger(UserDaoInMemoryDatabaseTest.class.getName());

    @Before
    public void createTable() throws SQLException {

        String createUserAccountTable = "CREATE TABLE IF NOT EXISTS user_account\n" +
                "(\n" +
                "    id                BIGINT(100) NOT NULL AUTO_INCREMENT,\n" +
                "    user_name         VARCHAR(200) NOT NULL,\n" +
                "    user_password     VARCHAR(100) NOT NULL,\n" +
                "    user_email        VARCHAR(200) NOT NULL,\n" +
                "    is_active         TINYINT      NOT NULL DEFAULT 1,\n" +
                "    user_phone_number VARCHAR(13)  NOT NULL,\n" +
                "    loyalty_points_id BIGINT(100) NOT NULL,\n" +
                "    wallet_id         BIGINT(100) NOT NULL,\n" +
                "    user_address_id   INT          NOT NULL,\n" +
                "    PRIMARY KEY (id, loyalty_points_id, wallet_id, user_address_id),\n" +
                ")";

        String createLoyaltyTable = "CREATE TABLE IF NOT EXISTS loyalty_points (\n" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT,\n" +
                "  points_amount INT NOT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (id),\n" +
                ")";

        String createWalletTable = "CREATE TABLE IF NOT EXISTS wallet (\n" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT,\n" +
                "  money_amount DECIMAL(2) NOT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (id),\n" +
                ")";

        String createUserAddressTable = "CREATE TABLE IF NOT EXISTS user_address (\n" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT,\n" +
                "  user_address_string VARCHAR(500) NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                ")";

        String createUserRoleTable = "CREATE TABLE IF NOT EXISTS user_role (\n" +
                "  id BIGINT(100) NOT NULL AUTO_INCREMENT,\n" +
                "  role_name VARCHAR(100) NOT NULL,\n" +
                "  PRIMARY KEY (id),\n" +
                ")";

        String createAccountToRolesTable = "CREATE TABLE IF NOT EXISTS account_to_roles (\n" +
                "  user_account_id BIGINT(100) NOT NULL,\n" +
                "  user_role_id BIGINT(100) NOT NULL,\n" +
                "  PRIMARY KEY (user_account_id, user_role_id),\n" +
                ")";

        String insertRolesInRoleTable = "INSERT INTO user_role (role_name) VALUE ('CLIENT');";

        ApplicationContext.initialize();

        executeSql(createLoyaltyTable);
        executeSql(createWalletTable);
        executeSql(createUserAddressTable);
        executeSql(createUserAccountTable);
        executeSql(createUserRoleTable);
        executeSql(createAccountToRolesTable);
        executeSql(insertRolesInRoleTable);
    }

    @After
    public void dropTable() throws SQLException {

        String dropUserAccountTable = "DROP TABLE user_account";
        String dropLoyaltyTable = "DROP TABLE loyalty_points";
        String dropWalletTable = "DROP TABLE wallet";
        String dropUserAddressTable = "DROP TABLE user_address";
        String dropUserRoleTable = "DROP TABLE user_role";
        String dropAccountToRolesTable = "DROP TABLE account_to_roles";

        executeSql(dropLoyaltyTable);
        executeSql(dropWalletTable);
        executeSql(dropUserAddressTable);
        executeSql(dropUserAccountTable);
        executeSql(dropUserRoleTable);
        executeSql(dropAccountToRolesTable);

        ApplicationContext.getInstance().destroy();
    }

    @Test
    public void shouldRegisterUserInNewTransaction() throws SQLException, ConnectionException {

        UserService userService = ApplicationContext.getInstance().getBean(UserService.class);
        Assert.assertNotNull(userService);

        User user = new User();
        user.setName("test1");
        user.setEmail("test1@test.com");
        user.setPassword("Test1111");
        user.setPhoneNumber("+375291234567");
        user.getUserAddress().setFullAddress("test_address");

        userService.register(user);

        List<User> allUsers = userService.findAll();
        for (User user1 : allUsers) {
            System.out.println(user1.toString());
            LOGGER.info(user1.toString());
        }
        Assert.assertEquals(1, allUsers.size());
    }

    private void executeSql(String sql) throws SQLException {
        ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

}
