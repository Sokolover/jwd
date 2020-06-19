package by.training.sokolov.service.role;

import by.training.sokolov.model.UserRole;
import by.training.sokolov.service.GenericService;

import java.sql.SQLException;

public interface UserRoleService extends GenericService<UserRole> {

    Long getIdByRoleName(UserRole userRole) throws SQLException;
}
