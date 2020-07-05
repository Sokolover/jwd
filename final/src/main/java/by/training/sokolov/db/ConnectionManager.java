package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager {

    Connection getConnection() throws SQLException, ConnectionException;
}
