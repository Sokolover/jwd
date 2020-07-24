package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.service.DishService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static by.training.sokolov.command.constants.CommandReturnValues.VIEW_ORDER_DISH_LIST_RESULT;
import static by.training.sokolov.core.constants.CommonAppConstants.DISH_ID_JSP_PARAM;
import static by.training.sokolov.core.constants.CommonAppConstants.MESSAGE_JSP_ATTRIBUTE;

public class DeleteDishCommand implements Command {

    private final DishService dishService;

    public DeleteDishCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String itemIdString = request.getParameter(DISH_ID_JSP_PARAM);
        Long itemIdLong = Long.parseLong(itemIdString);
        dishService.deleteById(itemIdLong);
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "Dish has been deleted from menu");

        return VIEW_ORDER_DISH_LIST_RESULT;
    }

}
