package by.training.sokolov.entity.role.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.role.model.UserRole;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserRoleDaoImpl extends GenericDao<UserRole> implements UserRoleDao {

    private static final Logger LOGGER = Logger.getLogger(UserRoleDaoImpl.class.getName());
    private static final String TABLE_NAME = "user_role";
    private static final String SELECT_ROLE_ID_QUERY = "SELECT *\n" +
            "FROM user_role\n" +
            "WHERE user_role.role_name LIKE {0}";
    private static final String INSERT_ROLES_QUERY = "INSERT INTO account_to_roles (user_account_id, user_role_id) VALUES ({0}, {1})";
    private static final String SELECT_ROLES_ID_QUERY =
            "SELECT user_role.*\n" +
                    "FROM user_role,\n" +
                    "     user_account,\n" +
                    "     account_to_roles\n" +
                    "WHERE user_account.id = account_to_roles.user_account_id\n" +
                    "  AND account_to_roles.user_role_id = user_role.id\n" +
                    "  AND user_account.id = {0}";
    private final Lock connectionLock = new ReentrantLock();

    private final ConnectionManager connectionManager;

    public UserRoleDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getUserRoleRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<UserRole> getUserRoleRowMapper() {

        return new IdentifiedRowMapper<UserRole>() {

            @Override
            public UserRole map(ResultSet resultSet) throws SQLException {
                UserRole userRole = new UserRole();
                userRole.setId(resultSet.getLong("id"));
                userRole.setRoleName(resultSet.getString("role_name"));
                return userRole;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList("money_amount");
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserRole entity) throws SQLException {

                statement.setString(1, entity.getRoleName());
            }
        };
    }

    @Override
    public UserRole getByName(String roleName) throws SQLException, ConnectionException {

/*
 ! обязательно нужны кавычки в
 String roleNameColumn = "'" + userRole.getRoleName() + "'";
 */
        connectionLock.lock();
        String roleNameColumn = "'" + roleName + "'";
        String sql = MessageFormat.format(SELECT_ROLE_ID_QUERY, roleNameColumn);
        AtomicReference<UserRole> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                try {
                    result.set(getUserRoleRowMapper().map(resultSet));
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            return result.get();
        } catch (ConnectionException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public List<UserRole> getUserRoles(User user) throws SQLException, ConnectionException {

        connectionLock.lock();
        String sql = MessageFormat.format(SELECT_ROLES_ID_QUERY, user.getId());
        List<UserRole> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    result.add(getUserRoleRowMapper().map(resultSet));
                } catch (IOException e) {
                    LOGGER.error(e.getMessage());
                }
            }
            return result;
        } catch (ConnectionException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public void setUserRoles(User user) throws SQLException, ConnectionException {

        connectionLock.lock();
        try (Connection connection = connectionManager.getConnection()) {
            for (UserRole role : user.getRoles()) {
                String sql = MessageFormat.format(INSERT_ROLES_QUERY, user.getId(), role.getId());
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.executeUpdate();
                }
            }
        } catch (ConnectionException e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            connectionLock.unlock();
        }
    }
}
