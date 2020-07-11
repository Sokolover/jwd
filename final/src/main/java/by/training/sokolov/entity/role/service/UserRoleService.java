package by.training.sokolov.entity.role.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.role.model.UserRole;

import java.sql.SQLException;

public interface UserRoleService extends GenericService<UserRole> {

    UserRole getByRoleName(String roleName) throws SQLException, ConnectionException;
}
