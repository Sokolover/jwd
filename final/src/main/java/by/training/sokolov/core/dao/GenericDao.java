package by.training.sokolov.core.dao;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static by.training.sokolov.core.constants.LoggerConstants.*;
import static java.lang.String.format;

public class GenericDao<T extends IdentifiedRow> implements CrudDao<T> {

    private static final Logger LOGGER = Logger.getLogger(GenericDao.class.getName());

    private static final String SELECT_ALL_QUERY = "SELECT * FROM {0}";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM {0} WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO {0} ({1}) VALUES ({2})";
    private static final String UPDATE_QUERY = "UPDATE {0} SET {1} WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM {0} WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM {0} WHERE id = ?";

    private final Lock connectionLock = new ReentrantLock();
    private final String tableName;
    private final IdentifiedRowMapper<T> rowMapper;
    private final ConnectionManager connectionManager;

    public GenericDao(String tableName, IdentifiedRowMapper<T> rowMapper, ConnectionManager connectionManager) {
        this.tableName = tableName;
        this.rowMapper = rowMapper;
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(T entity) throws SQLException, ConnectionException {

        connectionLock.lock();
        /*
        TODO сделать логгирование методов по образцу
         */
        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_MESSAGE, this.getClass().getSimpleName(), "[save]", entity.toString()));
        AtomicLong id = new AtomicLong(-1L);

        try (Connection connection = connectionManager.getConnection()) {
            List<String> columnNames = rowMapper.getColumnNames();
            String columns = String.join(", ", columnNames);
            String wildcards = columnNames.stream()
                    .map(column -> "?")
                    .collect(Collectors.joining(", "));

            String sql = MessageFormat.format(INSERT_QUERY, tableName, columns, wildcards);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                rowMapper.populateStatement(statement, entity);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                while (generatedKeys.next()) {
                    id.set(generatedKeys.getLong(1));
                }
            }
            return id.get();
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public void update(T entity) throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_MESSAGE, this.getClass().getSimpleName(), "[update]", entity.toString()));
        try (Connection connection = connectionManager.getConnection()) {
            List<String> columnNames = rowMapper.getColumnNames();
            String columns = columnNames.stream()
                    .map(column -> column + " = ?")
                    .collect(Collectors.joining(", "));

            String sql = MessageFormat.format(UPDATE_QUERY, tableName, columns);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                rowMapper.populateStatement(statement, entity);
                statement.setLong(columnNames.size() + 1, entity.getId());
                statement.executeUpdate();
            }
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public void deleteById(Long id) throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_ID_MESSAGE, this.getClass().getSimpleName(), "[deleteById]", id));
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(DELETE_BY_ID_QUERY, tableName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public void delete(T entity) throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_MESSAGE, this.getClass().getSimpleName(), "[delete]", entity.toString()));
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(DELETE_QUERY, tableName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, entity.getId());
                statement.executeUpdate();
            }
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public T getById(Long id) throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info(format(CLASS_INVOKED_METHOD_FOR_ENTITY_ID_MESSAGE, this.getClass().getSimpleName(), "[getById]", id));
        AtomicReference<T> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_BY_ID_QUERY, tableName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(rowMapper.map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                        return null;
                    }
                }
            }
            LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), "[getById]", result.get().toString()));
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public List<T> findAll() throws SQLException, ConnectionException {

        connectionLock.lock();
        LOGGER.info(format(CLASS_INVOKED_METHOD_MESSAGE, this.getClass().getSimpleName(), "[findAll]"));
        List<T> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_ALL_QUERY, tableName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    try {
                        result.add(rowMapper.map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                        return new ArrayList<>();
                    }
                }
            }
            return result;
        } finally {
            connectionLock.unlock();
        }
    }
}
