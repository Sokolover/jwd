package by.training.sokolov.entity.dishfeedback.service;

import by.training.sokolov.core.service.GenericServiceImpl;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.dishfeedback.dao.DishFeedbackDao;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;
import org.apache.log4j.Logger;

import java.sql.SQLException;

import static by.training.sokolov.core.constants.LoggerConstants.CLASS_INVOKED_METHOD_MESSAGE;
import static java.lang.String.format;

public class DishFeedbackServiceImpl extends GenericServiceImpl<DishFeedback> implements DishFeedbackService {

    private static final Logger LOGGER = Logger.getLogger(DishFeedbackServiceImpl.class.getName());

    private DishFeedbackDao dishFeedbackDao;

    public DishFeedbackServiceImpl(DishFeedbackDao dao) {
        super(dao);
        this.dishFeedbackDao = dao;
    }

    @Override
    public DishFeedback getByUserIdAndDishId(Long userId, Long dishId) throws ConnectionException, SQLException {

        String nameOfCurrentMethod = new Object() {
        }
                .getClass()
                .getEnclosingMethod()
                .getName();

        LOGGER.info(format(CLASS_INVOKED_METHOD_MESSAGE, this.getClass().getSimpleName(), nameOfCurrentMethod));
        return dishFeedbackDao.getByUserIdAndDishId(userId, dishId);
    }

    @Override
    public void update(DishFeedback entity) throws SQLException, ConnectionException {

        super.update(entity);
    }

    @Override
    public Long save(DishFeedback entity) throws SQLException, ConnectionException {

        return super.save(entity);
    }
}
