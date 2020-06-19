package by.training.sokolov.dao;

import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.dto.user.UserDto;
import by.training.sokolov.model.UserRole;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.UserRoleServiceImpl;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserDaoImpl extends GenericDao<UserDto> implements UserDao {

    private final static Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());
    private static final String TABLE_NAME = "user_account";
    private final Lock connectionLock = new ReentrantLock();

    public UserDaoImpl() {
        super(TABLE_NAME, getUserRowMapper());
    }

    private static IdentifiedRowMapper<UserDto> getUserRowMapper() {

        //todo поменять таблицы чтобы пользоваьтель мог с них читать (роль и фидбек)
        return new IdentifiedRowMapper<UserDto>() {

            @Override
            public UserDto map(ResultSet resultSet) throws SQLException {
                UserDto userDto = new UserDto();
                userDto.setId(resultSet.getLong("id"));
                userDto.setName(resultSet.getString("user_name"));
                userDto.setPassword(resultSet.getString("user_password"));
                userDto.setEmail(resultSet.getString("user_email"));
                userDto.setActive(resultSet.getBoolean("is_active"));
                userDto.setPhoneNumber(resultSet.getString("user_phone_number"));
                userDto.getLoyalty().setId(resultSet.getLong("loyalty_points_id"));
                userDto.getWallet().setId(resultSet.getLong("wallet_id"));
                userDto.getUserAddress().setId(resultSet.getLong("user_address_id"));
                return userDto;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("user_name",
                        "user_password",
                        "user_email",
                        "is_active",
                        "user_phone_number",
                        "loyalty_points_id",
                        "wallet_id",
                        "user_address_id");
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserDto entity) throws SQLException {

                statement.setString(1, entity.getName());
                statement.setString(2, entity.getPassword());
                statement.setString(3, entity.getEmail());
                statement.setBoolean(4, entity.isActive());
                statement.setString(5, entity.getPhoneNumber());
                statement.setLong(6, entity.getLoyalty().getId());
                statement.setLong(7, entity.getWallet().getId());
                statement.setLong(8, entity.getUserAddress().getId());
            }
        };
    }

    private void getUserRoles(UserDto userDto) throws SQLException {

        final String SELECT_ROLES_ID = "SELECT user_role.id\n" +
                "FROM user_role,\n" +
                "     user_account,\n" +
                "     account_to_roles\n" +
                "WHERE user_account.id = account_to_roles.user_account_id\n" +
                "  AND account_to_roles.user_role_id = user_role.id\n" +
                "  AND user_account.id = {0}";

        connectionLock.lock();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            String sql = MessageFormat.format(SELECT_ROLES_ID, userDto.getId());
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                List<UserRole> roles = new ArrayList<>();
                GenericService<UserRole> userRoleService = UserRoleServiceImpl.getInstance();
                while (resultSet.next()) {
                    UserRole userRole = userRoleService.getById(resultSet.getLong("id"));
                    roles.add(userRole);
                }
                userDto.setRoles(roles);
            }
        } finally {
            connectionLock.unlock();
        }
    }

    private void setUserRoles(UserDto userDto) throws SQLException {

        final String INSERT_ROLES = "INSERT INTO account_to_roles (user_account_id, user_role_id) VALUES ({0}, {1})";

        connectionLock.lock();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            for (UserRole role : userDto.getRoles()) {
                String sql = MessageFormat.format(INSERT_ROLES, userDto.getId(), role.getId());
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                }
            }
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public Long save(UserDto entity) throws SQLException {

        Long userId = super.save(entity);
        entity.setId(userId);
        setUserRoles(entity);

        return entity.getId();
    }

    @Override
    public List<UserDto> findAll() throws SQLException {

        List<UserDto> userDtos = super.findAll();

        for (UserDto userDto : userDtos) {
            getUserRoles(userDto);
        }

        //todo достать из других юзеровских таблиц инфу



        return userDtos;
    }

}
