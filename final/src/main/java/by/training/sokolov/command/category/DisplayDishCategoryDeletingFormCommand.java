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

import static by.training.sokolov.core.constants.CommonAppConstants.CATEGORY_LIST_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.DELETE_CATEGORY_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class DisplayDishCategoryDeletingFormCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DisplayDishCategoryDeletingFormCommand.class.getName());

    private final DishCategoryService dishCategoryService;

    public DisplayDishCategoryDeletingFormCommand(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        List<DishCategory> dishCategories = dishCategoryService.findAll();
        request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories.toString()));

        return DELETE_CATEGORY_FORM_JSP;
    }
}
