package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class BasicTransactionManager implements TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger(BasicTransactionManager.class);
    private final ConnectionPool<Connection> connectionPool;
    private final ThreadLocal<Connection> currentConnection = new ThreadLocal<>();

    public BasicTransactionManager(ConnectionPool<Connection> connectionPool) {
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
