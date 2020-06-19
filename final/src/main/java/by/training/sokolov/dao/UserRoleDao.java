package by.training.sokolov.dao;

import by.training.sokolov.model.UserRole;

import java.sql.SQLException;

public interface UserRoleDao extends CRUDDao<UserRole> {

    Long getIdByRoleName(UserRole userRole) throws SQLException;
}
