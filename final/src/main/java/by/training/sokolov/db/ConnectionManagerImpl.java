package by.training.sokolov.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerImpl.class.getName());
    private final ConnectionPool connectionPool;
    private final TransactionManager transactionManager;

    public ConnectionManagerImpl(ConnectionPool connectionPool, TransactionManager transactionManager) {
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    @Override
    public Connection getConnection() throws ConnectionException, SQLException {
        if (transactionManager.isEmpty()) {
            return connectionPool.getConnection();
        } else {
            return transactionManager.getConnection();
        }
    }
}
