package by.training.sokolov.command.category;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.service.DishCategoryService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.String.format;

public class DeleteDishCategoryFormSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteDishCategoryFormSubmitCommand.class.getName());

    private final DishCategoryService dishCategoryService;

    public DeleteDishCategoryFormSubmitCommand(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String categoryName = request.getParameter(DISH_CATEGORY_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_CATEGORY_NAME_JSP_PARAM, categoryName));

        dishCategoryService.deleteByName(categoryName);

        String message = "Dish category has been deleted from menu";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISH_ID_JSP_PARAM));

        return COMMAND_RESULT_MESSAGE_JSP;
    }
}
