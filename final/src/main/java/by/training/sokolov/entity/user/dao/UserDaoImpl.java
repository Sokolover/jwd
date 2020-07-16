package by.training.sokolov.entity.user.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserDaoImpl extends GenericDao<User> implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class.getName());
    private static final String TABLE_NAME = "user_account";
    private static final String SELECT_BY_NAME = "" +
            "SELECT {0}.*\n" +
            "FROM {0}\n" +
            "WHERE {0}.user_name = ?";
    private static final String SELECT_BY_EMAIL = "" +
            "SELECT {0}.*\n" +
            "FROM {0}\n" +
            "WHERE {0}.user_email = ?";
    private final Lock connectionLock = new ReentrantLock();
    private final ConnectionManager connectionManager;

    public UserDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getUserRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<User> getUserRowMapper() {

        return new IdentifiedRowMapper<User>() {

            @Override
            public User map(ResultSet resultSet) throws SQLException {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("user_name"));
                user.setPassword(resultSet.getString("user_password"));
                user.setEmail(resultSet.getString("user_email"));
                user.setActive(resultSet.getBoolean("is_active"));
                user.setPhoneNumber(resultSet.getString("user_phone_number"));
                user.getLoyalty().setId(resultSet.getLong("loyalty_points_id"));
                user.getWallet().setId(resultSet.getLong("wallet_id"));
                user.getUserAddress().setId(resultSet.getLong("user_address_id"));
                return user;
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
            public void populateStatement(PreparedStatement statement, User entity) throws SQLException {

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

    @Override
    public User getByName(String name) throws SQLException, ConnectionException {

        LOGGER.info("getByName(String name)");
        return getByStringParam(SELECT_BY_EMAIL, name);
    }

    @Override
    public User getByEmail(String email) throws SQLException, ConnectionException {

        LOGGER.info("getByEmail(String email)");
        return getByStringParam(SELECT_BY_EMAIL, email);
    }

    private User getByStringParam(String query, String param) throws SQLException, ConnectionException {

        connectionLock.lock();
        AtomicReference<User> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(query, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, param);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(getUserRowMapper().map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }
}
