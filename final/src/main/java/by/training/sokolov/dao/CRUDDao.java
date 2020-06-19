package by.training.sokolov.dao;

import java.sql.SQLException;
import java.util.List;

public interface CRUDDao<T extends IdentifiedRow> {

    Long save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(T entity) throws SQLException;

    T getById(Long id) throws SQLException;

    List<T> findAll() throws SQLException;
}
