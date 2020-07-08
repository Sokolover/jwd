package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.application.constants.JspName.DISH_FEEDBACK_WRITE_JSP;

public class DishFeedbackWriteCommand implements Command {

    private final DishService dishService;

    public DishFeedbackWriteCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String dishId = request.getParameter("dish.id");
        Long dishIdLong = Long.parseLong(dishId);

        Dish dish = dishService.getById(dishIdLong);

        request.setAttribute("dish", dish);

        return DISH_FEEDBACK_WRITE_JSP;
    }
}
