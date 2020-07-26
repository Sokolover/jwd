package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.service.DishService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.JspName.DISH_CREATE_FEEDBACK_JSP;

public class WritingDishFeedbackFormDisplayCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(WritingDishFeedbackFormDisplayCommand.class.getName());

    private final DishService dishService;

    public WritingDishFeedbackFormDisplayCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        JspUtil.setDishAttributeByDishParam(request, dishService);
        LOGGER.info("Command have been processed");

        return DISH_CREATE_FEEDBACK_JSP;
    }

}
