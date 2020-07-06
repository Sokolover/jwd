package by.training.sokolov.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagerImpl implements TransactionManager {

    private static final Logger LOGGER = Logger.getLogger(TransactionManagerImpl.class);
    private final ConnectionPool connectionPool;
    private final ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public TransactionManagerImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() {
        return currentConnection.get();
    }

    @Override
    public void begin() throws ConnectionException {
        if (this.isEmpty()) {
            try {
                Connection connection = connectionPool.getConnection();
                currentConnection.set(connection);
                connection.setAutoCommit(false);
            } catch (SQLException sqlException) {
                LOGGER.error("An exception is occurred during beginning of transaction");
                throw new ConnectionException(sqlException.getMessage(), sqlException);
            }
        }
    }

    @Override
    public void commit() throws ConnectionException {
        try {
            Connection connection = currentConnection.get();
            connection.commit();
            this.close();
        } catch (SQLException sqlException) {
            LOGGER.error("An exception is occurred during commission of transaction");
            throw new ConnectionException(sqlException.getMessage(), sqlException);
        }
    }

    @Override
    public void rollback() throws ConnectionException {
        try {
            Connection connection = currentConnection.get();
            connection.rollback();
            this.close();
        } catch (SQLException sqlException) {
            LOGGER.error("An exception is occurred during roll backing of transaction");
            throw new ConnectionException(sqlException.getMessage(), sqlException);
        }
    }

    private void close() throws ConnectionException {
        try {
            Connection connection = currentConnection.get();
            connection.setAutoCommit(true);
            connection.close();
            currentConnection.remove();
        } catch (SQLException sqlException) {
            LOGGER.error("An exception is occurred during closing of transaction");
            throw new ConnectionException(sqlException.getMessage(), sqlException);
        }
    }

    @Override
    public boolean isEmpty() {
        Connection connection = currentConnection.get();
        return connection == null;
    }
}
