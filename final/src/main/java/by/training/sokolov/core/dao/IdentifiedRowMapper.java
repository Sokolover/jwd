package by.training.sokolov.core.dao;

import by.training.sokolov.db.ConnectionException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IdentifiedRowMapper<T extends IdentifiedRow> {

    T map(ResultSet resultSet) throws SQLException, IOException;

    List<String> getColumnNames();

    void populateStatement(PreparedStatement statement, T entity) throws SQLException, ConnectionException;
}
