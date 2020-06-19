package by.training.sokolov.service;

import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.UserRoleDao;
import by.training.sokolov.dao.UserRoleDaoImpl;
import by.training.sokolov.model.UserRole;

import java.sql.SQLException;

public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService{

    private static UserRoleService userRoleService;
    private UserRoleDao userRoleDao;

    private UserRoleServiceImpl(CRUDDao<UserRole> dao) {
        super(dao);
        this.userRoleDao = (UserRoleDao) dao;
    }

    public static UserRoleService getInstance() {
        if (userRoleService == null) {
            CRUDDao<UserRole> userRoleDao = new UserRoleDaoImpl();
            userRoleService = new UserRoleServiceImpl(userRoleDao);
        }
        return userRoleService;
    }

    public Long getIdByRoleName(UserRole userRole) throws SQLException {

        return userRoleDao.getIdByRoleName(userRole);
    }
}
