package by.training.sokolov.db;

import java.sql.Connection;

public interface TransactionManager {

    Connection getConnection();

    void begin() throws ConnectionException;

    void commit() throws ConnectionException;

    void rollback() throws ConnectionException;

    boolean isEmpty();
}
