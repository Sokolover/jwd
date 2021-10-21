package by.training.sokolov.command.feedback;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.dishfeedback.model.DishFeedback;
import by.training.sokolov.entity.dishfeedback.service.DishFeedbackService;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.Objects.isNull;

public class SubmitDishFeedbackCreatingFormCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(SubmitDishFeedbackCreatingFormCommand.class.getName());

    private final DishFeedbackService dishFeedbackService;

    public SubmitDishFeedbackCreatingFormCommand(DishFeedbackService dishFeedbackService) {
        this.dishFeedbackService = dishFeedbackService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String rating = request.getParameter(FEEDBACK_RATING_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, FEEDBACK_RATING_JSP_PARAM, rating));

        String comment = request.getParameter(FEEDBACK_TEXT_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, FEEDBACK_TEXT_JSP_PARAM, comment));

        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_ID_JSP_PARAM, dishIdString));

        String sessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(sessionId);
        Long currentUserId = currentUser.getId();

        DishFeedback dishFeedback = dishFeedbackService.getByUserIdAndDishId(currentUserId, parseLong(dishIdString));

        String message;
        if (isNull(dishFeedback)) {

            saveNewFeedback(currentUserId, rating, comment, dishIdString);
            message = "New feedback saved";
        } else {

            updateExistingFeedback(rating, comment, dishFeedback);
            message = "Existing feedback updated";
        }

        LOGGER.info(message);
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, MESSAGE_JSP_ATTRIBUTE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private void updateExistingFeedback(String rating, String comment, DishFeedback dishFeedback) throws SQLException, ConnectionException {

        dishFeedback.setDishRating(parseInt(rating));
        dishFeedback.setDishComment(comment);
        dishFeedbackService.update(dishFeedback);
    }

    private void saveNewFeedback(Long currentUserId, String rating, String comment, String dishIdString) throws SQLException, ConnectionException {

        DishFeedback dishFeedback = new DishFeedback();
        dishFeedback.setUserId(currentUserId);
        dishFeedback.setDishRating(parseInt(rating));
        dishFeedback.setDishComment(comment);
        dishFeedback.setDishId(parseLong(dishIdString));
        dishFeedbackService.save(dishFeedback);
    }
}
