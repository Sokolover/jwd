package by.training.sokolov.entity.user.service;

import by.training.sokolov.core.service.GenericService;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.user.model.User;

import java.sql.SQLException;

public interface UserService extends GenericService<User> {

    void register(User user) throws ConnectionException, SQLException;

    User login(String email, String password) throws ConnectionException, SQLException;

    User getByEmail(String email) throws ConnectionException, SQLException;
}
