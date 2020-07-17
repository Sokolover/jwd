package by.training.sokolov.command;

import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;
import by.training.sokolov.entity.dishfeedback.service.DishFeedbackService;
import by.training.sokolov.entity.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.DISH_LIST_JSP;

public class DishFeedbackSubmitCommand implements Command {

    private final DishFeedbackService dishFeedbackService;

    public DishFeedbackSubmitCommand(DishFeedbackService dishFeedbackService) {
        this.dishFeedbackService = dishFeedbackService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(sessionId);
        Long currentUserId = currentUser.getId();
        String rating = request.getParameter(FEEDBACK_RATING_JSP_PARAM);
        String comment = request.getParameter(FEEDBACK_TEXT_JSP_PARAM);
        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        Long dishIdLong = Long.parseLong(dishIdString);

        DishFeedback dishFeedback = dishFeedbackService.getUsersFeedbackByDishId(currentUserId, dishIdLong);

        if (Objects.isNull(dishFeedback)) {
            saveNewFeedback(currentUserId, rating, comment, dishIdString);
        } else {
            updateExistingFeedback(rating, comment, dishFeedback);
        }

        return DISH_LIST_JSP;
    }

    private void updateExistingFeedback(String rating, String comment, DishFeedback dishFeedback) throws SQLException, ConnectionException {

        dishFeedback.setDishRating(Integer.parseInt(rating));
        dishFeedback.setDishComment(comment);
        dishFeedbackService.update(dishFeedback);
    }

    private void saveNewFeedback(Long currentUserId, String rating, String comment, String dishIdString) throws SQLException, ConnectionException {

        DishFeedback dishFeedback = new DishFeedback();
        dishFeedback.getUser().setId(currentUserId);
        dishFeedback.setDishRating(Integer.parseInt(rating));
        dishFeedback.setDishComment(comment);
        dishFeedback.getDish().setId(Long.parseLong(dishIdString));
        dishFeedbackService.save(dishFeedback);
    }
}
