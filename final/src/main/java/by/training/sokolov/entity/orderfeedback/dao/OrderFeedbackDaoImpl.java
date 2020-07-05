package by.training.sokolov.orderfeedback.dao;

import by.training.sokolov.dao.GenericDao;
import by.training.sokolov.dao.IdentifiedRowMapper;
import by.training.sokolov.orderfeedback.model.OrderFeedback;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class OrderFeedbackDaoImpl extends GenericDao<OrderFeedback> implements OrderFeedbackDao {

    private static final String TABLE_NAME = "order_feedback";

    public OrderFeedbackDaoImpl() {
        super(TABLE_NAME, getOrderFeedbackRowMapper());
    }

    private static IdentifiedRowMapper<OrderFeedback> getOrderFeedbackRowMapper() {

        return new IdentifiedRowMapper<OrderFeedback>() {

            @Override
            public OrderFeedback map(ResultSet resultSet) throws SQLException, IOException {
                OrderFeedback orderFeedback = new OrderFeedback();
                orderFeedback.setId(resultSet.getLong("id"));
                orderFeedback.setOrderRating(resultSet.getInt("order_rating"));
                orderFeedback.setOrderComment(resultSet.getString("order_comment"));
                return orderFeedback;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("order_rating",
                        "order_comment");
            }

            @Override
            public void populateStatement(PreparedStatement statement, OrderFeedback entity) throws SQLException {

                statement.setInt(1, entity.getOrderRating());
                statement.setString(2, entity.getOrderComment());
            }
        };
    }
}
