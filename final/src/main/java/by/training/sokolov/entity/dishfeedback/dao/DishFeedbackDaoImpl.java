package by.training.sokolov.entity.dishfeedback.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.db.ConnectionManager;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DishFeedbackDaoImpl extends GenericDao<DishFeedback> implements DishFeedbackDao {

    private static final  Logger LOGGER = Logger.getLogger(DishFeedbackDaoImpl.class.getName());
    private static final String TABLE_NAME = "dish_feedback";
    private static final String SELECT_FEEDBACK_BY_USER_ID_AND_DISH_ID = "" +
            "SELECT *\n" +
            "FROM {0}\n" +
            "WHERE {0}.dish_id = ?\n" +
            "  AND {0}.user_account_id = ?";
    private final Lock connectionLock = new ReentrantLock();
    private final ConnectionManager connectionManager;

    public DishFeedbackDaoImpl(ConnectionManager connectionManager) {
        super(TABLE_NAME, getDishFeedbackRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<DishFeedback> getDishFeedbackRowMapper() {

        return new IdentifiedRowMapper<DishFeedback>() {

            @Override
            public DishFeedback map(ResultSet resultSet) throws SQLException {
                DishFeedback dishFeedback = new DishFeedback();
                dishFeedback.setId(resultSet.getLong("id"));
                dishFeedback.setDishRating(resultSet.getInt("dish_rating"));
                dishFeedback.setDishComment(resultSet.getString("dish_comment"));
                dishFeedback.getUser().setId(resultSet.getLong("user_account_id"));
                dishFeedback.getDish().setId(resultSet.getLong("dish_id"));
                return dishFeedback;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList("dish_rating",
                        "dish_comment",
                        "user_account_id",
                        "dish_id");
            }

            @Override
            public void populateStatement(PreparedStatement statement, DishFeedback entity) throws SQLException {

                statement.setInt(1, entity.getDishRating());
                statement.setString(2, entity.getDishComment());
                statement.setLong(3, entity.getUser().getId());
                statement.setLong(4, entity.getDish().getId());
            }
        };
    }

    @Override
    public DishFeedback getUsersFeedbackByDishId(Long userId, Long dishId) throws ConnectionException, SQLException {

        connectionLock.lock();
//        LOGGER.info("getById()--" + id);
        AtomicReference<DishFeedback> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_FEEDBACK_BY_USER_ID_AND_DISH_ID, TABLE_NAME);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, dishId);
                statement.setLong(2, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    try {
                        result.set(getDishFeedbackRowMapper().map(resultSet));
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
}
