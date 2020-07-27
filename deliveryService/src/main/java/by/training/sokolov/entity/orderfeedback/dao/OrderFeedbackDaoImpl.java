package by.training.sokolov.entity.orderfeedback.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionManager;
import by.training.sokolov.entity.orderfeedback.model.OrderFeedback;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static by.training.sokolov.entity.orderfeedback.dao.OrderFeedbackTableConstants.*;

public class OrderFeedbackDaoImpl extends GenericDao<OrderFeedback> implements OrderFeedbackDao {

    private final ConnectionManager connectionManager;

    public OrderFeedbackDaoImpl(ConnectionManager connectionManager) {
        super(ORDER_FEEDBACK_TABLE_NAME, getOrderFeedbackRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<OrderFeedback> getOrderFeedbackRowMapper() {

        return new IdentifiedRowMapper<OrderFeedback>() {

            @Override
            public OrderFeedback map(ResultSet resultSet) throws SQLException, IOException {
                OrderFeedback orderFeedback = new OrderFeedback();
                orderFeedback.setId(resultSet.getLong(ID));
                orderFeedback.setOrderRating(resultSet.getInt(ORDER_RATING));
                orderFeedback.setOrderComment(resultSet.getString(ORDER_COMMENT));
                return orderFeedback;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList(ORDER_RATING,
                        ORDER_COMMENT);
            }

            @Override
            public void populateStatement(PreparedStatement statement, OrderFeedback entity) throws SQLException {

                statement.setInt(1, entity.getOrderRating());
                statement.setString(2, entity.getOrderComment());
            }
        };
    }
}
