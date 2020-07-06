package by.training.sokolov.entity.user.service;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.core.service.GenericService;

import java.sql.SQLException;

public interface UserService extends GenericService<User> {

    void register(User user) throws ConnectionException, SQLException;

    User login(String name, String password) throws ConnectionException, SQLException;
}