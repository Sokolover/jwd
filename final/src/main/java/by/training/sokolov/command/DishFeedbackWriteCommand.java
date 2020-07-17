package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.DISH_ID_JSP_PARAM;
import static by.training.sokolov.core.constants.CommonAppConstants.DISH_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.DISH_CREATE_FEEDBACK_JSP;

public class DishFeedbackWriteCommand implements Command {

    private final DishService dishService;

    public DishFeedbackWriteCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String dishId = request.getParameter(DISH_ID_JSP_PARAM);
        Long dishIdLong = Long.parseLong(dishId);
        Dish dish = dishService.getById(dishIdLong);
        request.setAttribute(DISH_JSP_ATTRIBUTE, dish);

        return DISH_CREATE_FEEDBACK_JSP;
    }
}
