//package by.training.sokolov.entity.role.service;
//
//import by.training.sokolov.db.ConnectionException;
//import by.training.sokolov.entity.role.dao.UserRoleDao;
//import by.training.sokolov.entity.role.model.UserRole;
//import by.training.sokolov.service.GenericServiceImpl;
//
//import java.sql.SQLException;
//
//public class UserRoleServiceImpl extends GenericServiceImpl<UserRole> implements UserRoleService {
//
//    private UserRoleDao userRoleDao;
//
//    public UserRoleServiceImpl(UserRoleDao dao) {
//        super(dao);
//        this.userRoleDao = dao;
//    }
//
//    @Override
//    public UserRole getByRoleName(String roleName) throws SQLException, ConnectionException {
//
//        return userRoleDao.getByName(roleName);
//    }
//}
