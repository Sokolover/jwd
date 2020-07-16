package by.training.sokolov.entity.user.dao;

import by.training.sokolov.core.dao.CrudDao;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User> {

    User getByName(String name) throws SQLException, ConnectionException;

    User getByEmail(String name) throws SQLException, ConnectionException;
}
