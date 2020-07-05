package by.sadko.training.connection;

import by.sadko.training.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class BasicConnectionManager implements ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger(BasicConnectionManager.class);
    private final ConnectionPool<Connection> connectionPool;
    private final TransactionManager transactionManager;

    public BasicConnectionManager(ConnectionPool<Connection> connectionPool, TransactionManager transactionManager) {
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    @Override
    public Connection getConnection() throws ConnectionException {
        if (transactionManager.isEmpty()) {
            return connectionPool.getConnection();
        } else {
            return transactionManager.getConnection();
        }
    }

    /*@Override
    public void beginTransaction(Connection connection) throws SQLException, ConnectionException {
        if (!connection.isClosed()) {
            connection.setAutoCommit(false);
            LOGGER.info("Transaction has been started");
        }
        LOGGER.info("Connection is closed");
    }

    @Override
    public void commitTransaction(Connection connection) throws SQLException {
        if (!connection.isClosed()) {
            connection.commit();
            connection.close();
            LOGGER.info("Transaction was committed");
        }
        LOGGER.info("Connection is closed");
    }

    @Override
    public void rollbackTransaction(Connection connection) throws SQLException {
        if (!connection.isClosed()) {
            connection.rollback();
            connection.close();
            LOGGER.info("Transaction was rolled back");
        }
        LOGGER.info("Connection is closed");
    }*/
}
