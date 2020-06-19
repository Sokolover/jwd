package by.training.sokolov.service;

import by.training.sokolov.model.UserRole;

import java.sql.SQLException;

public interface UserRoleService extends GenericService<UserRole> {

    Long getIdByRoleName(UserRole userRole) throws SQLException;
}
