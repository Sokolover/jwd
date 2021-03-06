package by.training.sokolov.entity.role.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.role.model.UserRole;
import by.training.sokolov.entity.user.dao.UserAccountTableConstants;
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

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_FOR_ENTITY_NAME_MESSAGE;
import static by.training.sokolov.entity.role.dao.AccountToRolesTableConstants.*;
import static by.training.sokolov.entity.role.dao.UserRoleTableConstants.*;
import static java.lang.String.format;

public class UserRoleDaoImpl extends GenericDao<UserRole> implements UserRoleDao {

    private static final Logger LOGGER = Logger.getLogger(UserRoleDaoImpl.class.getName());

    private static final String SELECT_ROLE_ID_QUERY = "" +
            "SELECT {0}.*\n" +
            "FROM {0}\n" +
            "WHERE {0}.{1} = ?";
    private static final String INSERT_ROLES_QUERY = "INSERT INTO {0} ({1}, {2}) VALUES (?, ?)";
    private static final String SELECT_ROLES_ID_QUERY = "" +
            "SELECT {0}.*\n" +
            "FROM {0},\n" +
            "     {2},\n" +
            "     {4}\n" +
            "WHERE {2}.{3} = {4}.{5}\n" +
            "  AND {4}.{6} = {0}.{1}\n" +
            "  AND {2}.{3} = ?";
    private final Lock connectionLock = new ReentrantLock();

    private final ConnectionManager connectionManager;

    public UserRoleDaoImpl(ConnectionManager connectionManager) {
        super(USER_ROLE_TABLE_NAME, getUserRoleRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<UserRole> getUserRoleRowMapper() {

        return new IdentifiedRowMapper<UserRole>() {

            @Override
            public UserRole map(ResultSet resultSet) throws SQLException {
                UserRole userRole = new UserRole();
                userRole.setId(resultSet.getLong(ID));
                userRole.setRoleName(resultSet.getString(ROLE_NAME));
                return userRole;
            }

            @Override
            public List<String> getColumnNames() {
                return Collections.singletonList(ROLE_NAME);
            }

            @Override
            public void populateStatement(PreparedStatement statement, UserRole entity) throws SQLException {

                statement.setString(1, entity.getRoleName());
            }
        };
    }

    @Override
    public UserRole getByName(String roleName) throws SQLException, ConnectionException {

        connectionLock.lock();

        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_NAME_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, roleName));

        String sql = MessageFormat.format(SELECT_ROLE_ID_QUERY, USER_ROLE_TABLE_NAME, ROLE_NAME);
        AtomicReference<UserRole> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, roleName);
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
        String sql = MessageFormat.format(SELECT_ROLES_ID_QUERY,
                UserRoleTableConstants.USER_ROLE_TABLE_NAME, UserRoleTableConstants.ID,
                UserAccountTableConstants.USER_ACCOUNT_TABLE_NAME, UserAccountTableConstants.ID,
                AccountToRolesTableConstants.ACCOUNT_TO_ROLES_TABLE_NAME, AccountToRolesTableConstants.USER_ACCOUNT_ID, AccountToRolesTableConstants.USER_ROLE_ID);

        List<UserRole> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, user.getId());
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
                String sql = MessageFormat.format(INSERT_ROLES_QUERY, ACCOUNT_TO_ROLES_TABLE_NAME, USER_ACCOUNT_ID, USER_ROLE_ID);
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setLong(1, user.getId());
                    statement.setLong(2, role.getId());
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
