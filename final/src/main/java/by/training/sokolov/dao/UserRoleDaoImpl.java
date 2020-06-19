package by.training.sokolov.dao;

import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.model.UserRole;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserRoleDaoImpl extends GenericDao<UserRole> implements UserRoleDao {

    private final static Logger LOGGER = Logger.getLogger(UserRoleDaoImpl.class.getName());
    private static final String TABLE_NAME = "user_role";
    private final Lock connectionLock = new ReentrantLock();

    public UserRoleDaoImpl() {
        super(TABLE_NAME, getUserRoleRowMapper());
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
    public Long getIdByRoleName(UserRole userRole) throws SQLException {

        String SELECT_ROLE_ID = "SELECT user_role.id\n" +
                "FROM user_role\n" +
                "WHERE user_role.role_name LIKE {0}";

        long roleId = 0L;
        connectionLock.lock();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            String roleNameColumn = "'" + userRole.getRoleName() + "'";
            String sql = MessageFormat.format(SELECT_ROLE_ID, roleNameColumn);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    roleId = resultSet.getLong("id");
                }
            }
            return roleId;
        } finally {
            connectionLock.unlock();
        }
    }
}
