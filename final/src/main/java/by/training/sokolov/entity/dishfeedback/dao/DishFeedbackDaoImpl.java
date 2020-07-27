package by.training.sokolov.entity.dishfeedback.dao;

import by.training.sokolov.core.dao.GenericDao;
import by.training.sokolov.core.dao.IdentifiedRowMapper;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.database.connection.ConnectionManager;
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

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_AND_GOT_MESSAGE;
import static by.training.sokolov.entity.dishfeedback.dao.DishFeedbackTableConstants.*;
import static java.lang.String.format;

public class DishFeedbackDaoImpl extends GenericDao<DishFeedback> implements DishFeedbackDao {

    private static final Logger LOGGER = Logger.getLogger(DishFeedbackDaoImpl.class.getName());


    private static final String SELECT_FEEDBACK_BY_USER_ID_AND_DISH_ID = "" +
            "SELECT *\n" +
            "FROM {0}\n" +
            "WHERE {0}.{1} = ?\n" +
            "  AND {0}.{2} = ?";
    private final Lock connectionLock = new ReentrantLock();
    private final ConnectionManager connectionManager;

    public DishFeedbackDaoImpl(ConnectionManager connectionManager) {
        super(DISH_FEEDBACK_TABLE_NAME, getDishFeedbackRowMapper(), connectionManager);
        this.connectionManager = connectionManager;
    }

    private static IdentifiedRowMapper<DishFeedback> getDishFeedbackRowMapper() {

        return new IdentifiedRowMapper<DishFeedback>() {

            @Override
            public DishFeedback map(ResultSet resultSet) throws SQLException {
                DishFeedback dishFeedback = new DishFeedback();
                dishFeedback.setId(resultSet.getLong(ID));
                dishFeedback.setDishRating(resultSet.getInt(DISH_RATING));
                dishFeedback.setDishComment(resultSet.getString(DISH_COMMENT));
                dishFeedback.setUserId(resultSet.getLong(USER_ACCOUNT_ID));
                dishFeedback.setDishId(resultSet.getLong(DISH_ID));
                return dishFeedback;
            }

            @Override
            public List<String> getColumnNames() {
                return Arrays.asList(DISH_RATING,
                        DISH_COMMENT,
                        USER_ACCOUNT_ID,
                        DISH_ID);
            }

            @Override
            public void populateStatement(PreparedStatement statement, DishFeedback entity) throws SQLException {

                statement.setInt(1, entity.getDishRating());
                statement.setString(2, entity.getDishComment());
                statement.setLong(3, entity.getUserId());
                statement.setLong(4, entity.getDishId());
            }
        };
    }

    @Override
    public DishFeedback getByUserIdAndDishId(Long userId, Long dishId) throws ConnectionException, SQLException {

        connectionLock.lock();

        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format("%s called %s method for entity with User id = [%s] and Dish id = [%s]", this.getClass().getSimpleName(), nameOfCurrentMethod, userId, dishId));

        AtomicReference<DishFeedback> result = new AtomicReference<>();
        try (Connection connection = connectionManager.getConnection()) {
            String sql = MessageFormat.format(SELECT_FEEDBACK_BY_USER_ID_AND_DISH_ID, DISH_FEEDBACK_TABLE_NAME, DISH_ID, USER_ACCOUNT_ID);
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
                } else {
                    LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, result.get()));
                    return null;
                }
            }
            LOGGER.info(format(CLASS_INVOKED_METHOD_AND_GOT_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod, result.get().toString()));
            return result.get();
        } finally {
            connectionLock.unlock();
        }
    }
}
