package by.training.sokolov.dao.role;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.model.UserRole;

import java.sql.SQLException;

public interface UserRoleDao extends CRUDDao<UserRole> {

    Long getIdByRoleName(UserRole userRole) throws SQLException;
}
