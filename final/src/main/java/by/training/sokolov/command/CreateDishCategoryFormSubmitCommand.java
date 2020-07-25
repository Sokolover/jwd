package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.CATEGORY_NAME_JSP_PARAM;
import static by.training.sokolov.core.constants.CommonAppConstants.MESSAGE_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.String.format;

public class CreateDishCategoryFormSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateDishCategoryFormSubmitCommand.class.getName());

    private final DishCategoryService dishCategoryService;

    public CreateDishCategoryFormSubmitCommand(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {
        /*
        todo валидация
         */
        String categoryName = request.getParameter(CATEGORY_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, CATEGORY_NAME_JSP_PARAM, categoryName));

        DishCategory dishCategory = new DishCategory();
        dishCategory.setCategoryName(categoryName);

        dishCategoryService.save(dishCategory);

        String message = "New category has been added to menu";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }
}
