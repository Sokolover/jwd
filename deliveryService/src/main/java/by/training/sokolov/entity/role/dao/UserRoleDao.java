package by.training.sokolov.entity.role.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.role.model.UserRole;
import by.training.sokolov.entity.user.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserRoleDao extends CrudDao<UserRole> {

    UserRole getByName(String roleName) throws SQLException, ConnectionException;

    List<UserRole> getUserRoles(User user) throws SQLException, ConnectionException;

    void setUserRoles(User user) throws SQLException, ConnectionException;
}
