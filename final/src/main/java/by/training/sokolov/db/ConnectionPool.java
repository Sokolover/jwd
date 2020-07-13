package by.training.sokolov.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

    Connection getConnection() throws SQLException;

    void releaseConnection(Connection connection);

    void shutdown() throws SQLException;

}
