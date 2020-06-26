package by.training.sokolov.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IdentifiedRowMapper<T extends IdentifiedRow> {

    T map(ResultSet resultSet) throws SQLException, IOException;

    List<String> getColumnNames();

    void populateStatement(PreparedStatement statement, T entity) throws SQLException;
}
