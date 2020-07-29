package by.training.sokolov.command.category;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.CREATE_CATEGORY_FORM_JSP;
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

        String categoryName = request.getParameter(CATEGORY_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, CATEGORY_NAME_JSP_PARAM, categoryName));

        if (!isUniqueCategoryName(request, categoryName)) {
            return CREATE_CATEGORY_FORM_JSP;
        }

        DishCategory dishCategory = new DishCategory();
        dishCategory.setCategoryName(categoryName);

        dishCategoryService.save(dishCategory);

        String message = "New category has been added to menu";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private boolean isUniqueCategoryName(HttpServletRequest request, String categoryName) throws SQLException, ConnectionException {

        List<DishCategory> dishCategories = dishCategoryService.findAll();

        for (DishCategory category : dishCategories) {

            if (category.getCategoryName().equalsIgnoreCase(categoryName)) {

                String message = "This category is already exist";
                request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                LOGGER.error(message);

                return true;
            }
        }
        return false;
    }
}
