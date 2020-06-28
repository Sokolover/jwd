package by.training.sokolov.role.dao;

import by.training.sokolov.dao.CrudDao;
import by.training.sokolov.role.model.UserRole;

import java.sql.SQLException;

public interface UserRoleDao extends CrudDao<UserRole> {

    Long getIdByRoleName(UserRole userRole) throws SQLException;
}
