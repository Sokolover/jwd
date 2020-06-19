package by.training.sokolov.service.role;

import by.training.sokolov.dao.role.UserRoleDao;
import by.training.sokolov.dao.role.UserRoleDaoImpl;
import by.training.sokolov.model.UserRole;
import by.training.sokolov.service.GenericServiceImpl;

import java.sql.SQLException;

public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService {

    private static UserRoleService userRoleService;
    private UserRoleDao userRoleDao;

    private UserRoleServiceImpl(UserRoleDao dao) {
        super(dao);
        this.userRoleDao = dao;
    }

    public static UserRoleService getInstance() {
        if (userRoleService == null) {
            UserRoleDao userRoleDao = new UserRoleDaoImpl();
            userRoleService = new UserRoleServiceImpl(userRoleDao);
        }
        return userRoleService;
    }

    public Long getIdByRoleName(UserRole userRole) throws SQLException {

        return userRoleDao.getIdByRoleName(userRole);
    }
}
