package by.training.sokolov.jdbc;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.UserDaoImpl;
import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.dto.user.UserDto;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserServiceTest {

    private Lock connectionLock = new ReentrantLock();

    @Test
    public void testFindAll() throws SQLException {

        CRUDDao<UserDto> userDao = new UserDaoImpl();
        GenericService<UserDto> userService = new UserService(userDao);
        List<UserDto> userDtos = userService.findAll();

        for (UserDto userDto : userDtos) {
            System.out.println(userDto);
        }


        Assert.assertEquals(2, userDtos.size());

    }

    @Test
    public void walletId() throws SQLException {

        CRUDDao<UserDto> userDao = new UserDaoImpl();
        GenericService<UserDto> userService = new UserService(userDao);

        UserDto userDto = new UserDto();
        userDto.setName("name");
        userDto.setPassword("password");
        userDto.setEmail("email");
        userDto.setPhoneNumber("phoneNumber");
//        userDto.setRoles(Collections.singletonList("CLIENT"));

        //todo все запросы должны быть в слое дао (наверное юзера)!
//        findUserRoles(userDto);
        userService.save(userDto);

        System.out.println(userDao);

        Assert.assertEquals(1, 1);

    }

//    private void findUserRoles(UserDto userDto) throws SQLException {
//        String q1 = "SELECT user_role.id\n" +
//                "FROM user_role,\n" +
//                "     user_account,\n" +
//                "     account_to_roles\n" +
//                "WHERE user_role.role_name LIKE {0}";
//
//        for (String role : userDto.getRoles()) {
//            connectionLock.lock();
//            try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
//                role = "'" + role + "'";
//                String sql = MessageFormat.format(q1, role);
//                try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                    ResultSet resultSet = statement.executeQuery();
//                    while (resultSet.next()) {
//                        setUserToRole(userDto, resultSet.getLong("id"));
//                    }
//                }
//                BasicConnectionPool.getInstance().releaseConnection(connection);
//            } finally {
//                connectionLock.unlock();
//            }
//        }
//    }

    private void setUserToRole(UserDto userDto, Long roleId) throws SQLException {

        String q2 = "INSERT INTO account_to_roles(user_account_id, user_role_id) VALUES({0}, {1})\n";

        /*
        todo
            сначала сохраняется пользователь в таблицу
            потом достается его id
            потом вставляется сюда для связывания юзера и его роли в таблице "account_to_roles"
         */

        /*
        todo
            сначала сделать тест всех методов а потом встявлять их в код!!!
         */

        connectionLock.lock();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            String sql = MessageFormat.format(q2, userDto.getId(), roleId);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
        } finally {
            connectionLock.unlock();
        }
    }
}
