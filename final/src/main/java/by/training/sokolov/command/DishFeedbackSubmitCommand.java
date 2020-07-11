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

import static by.training.sokolov.application.constants.JspName.DISH_LIST_JSP;

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
        String rating = request.getParameter("feedback.rating");
        String comment = request.getParameter("feedback.text");
        String dishIdString = request.getParameter("dish.id");
        Long dishIdLong = Long.parseLong(dishIdString);

        DishFeedback dishFeedback = dishFeedbackService.getUsersFeedbackByDishId(currentUserId, dishIdLong);

        if (Objects.isNull(dishFeedback)) {
            dishFeedback = new DishFeedback();
            dishFeedback.getUser().setId(currentUserId);
            dishFeedback.setDishRating(Integer.parseInt(rating));
            dishFeedback.setDishComment(comment);
            dishFeedback.getDish().setId(Long.parseLong(dishIdString));
            dishFeedbackService.save(dishFeedback);
        } else {
            dishFeedback.setDishRating(Integer.parseInt(rating));
            dishFeedback.setDishComment(comment);
            dishFeedbackService.update(dishFeedback);
        }

        return DISH_LIST_JSP;
    }
}
