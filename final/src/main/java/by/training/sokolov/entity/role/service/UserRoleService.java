package by.training.sokolov.role.service;

import by.training.sokolov.role.model.UserRole;
import by.training.sokolov.service.GenericService;

import java.sql.SQLException;

public interface UserRoleService extends GenericService<UserRole> {

    Long getIdByRoleName(UserRole userRole) throws SQLException;
}
