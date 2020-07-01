package by.training.sokolov.dao;

import by.training.sokolov.db.BasicConnectionPool;
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

public class GenericDao<T extends IdentifiedRow> implements CrudDao<T> {

    private static final Logger LOGGER = Logger.getLogger(GenericDao.class.getName());
    private final Lock connectionLock = new ReentrantLock();

    private static final String SELECT_ALL_QUERY = "SELECT * FROM {0}";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM {0} WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO {0} ({1}) VALUES ({2})";
    private static final String UPDATE_QUERY = "UPDATE {0} SET {1} WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM {0} WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM {0} WHERE id = ?";
    private static final String SELECT_MAX_ID_QUERY = "SELECT MAX(ID) FROM {0}";

    private final String tableName;
    private final IdentifiedRowMapper<T> rowMapper;

    public GenericDao(String tableName, IdentifiedRowMapper<T> rowMapper) {
        this.tableName = tableName;
        this.rowMapper = rowMapper;
    }

    @Override
    public Long save(T entity) throws SQLException {

        connectionLock.lock();
        LOGGER.info("save()--" + entity.toString());
        AtomicLong id = new AtomicLong(-1L);

        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
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
    public void update(T entity) throws SQLException {

        connectionLock.lock();
        LOGGER.info("update()--" + entity.toString());
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
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
    public void deleteById(Long id) throws SQLException {

        connectionLock.lock();
        LOGGER.info("deleteById()--" + id);
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
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
    public void delete(T entity) throws SQLException {

        connectionLock.lock();
        LOGGER.info("delete()--" + entity.toString());
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
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
    public T getById(Long id) throws SQLException {

        connectionLock.lock();
        LOGGER.info("getById()--" + id);
        AtomicReference<T> result = new AtomicReference<>();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            String sql = MessageFormat.format(SELECT_BY_ID_QUERY, tableName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(rowMapper.map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }

    @Override
    public List<T> findAll() throws SQLException {

        connectionLock.lock();
        LOGGER.info("findAll()");
        List<T> result = new ArrayList<>();
        try (Connection connection = BasicConnectionPool.getInstance().getConnection()) {
            String sql = MessageFormat.format(SELECT_ALL_QUERY, tableName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    try {
                        result.add(rowMapper.map(resultSet));
                    } catch (IOException e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            }
            return result;
        } finally {
            connectionLock.unlock();
        }
    }
}