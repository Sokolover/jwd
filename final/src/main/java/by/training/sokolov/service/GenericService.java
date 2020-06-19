package by.training.sokolov.service;

import java.sql.SQLException;
import java.util.List;

public interface GenericService<T> {

    Long save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    T getById(Long id) throws SQLException;

    List<T> findAll() throws SQLException;
}
