package by.training.sokolov.role.service;

import by.training.sokolov.role.dao.UserRoleDao;
import by.training.sokolov.role.model.UserRole;
import by.training.sokolov.service.GenericServiceImpl;

import java.sql.SQLException;

public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService {

    private UserRoleDao userRoleDao;

    public UserRoleServiceImpl(UserRoleDao dao) {
        super(dao);
        this.userRoleDao = dao;
    }

    @Override
    public Long getIdByRoleName(UserRole userRole) throws SQLException {

        return userRoleDao.getIdByRoleName(userRole);
    }
}
