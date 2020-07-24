package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.CATEGORY_NAME_JSP_PARAM;
import static by.training.sokolov.core.constants.CommonAppConstants.MESSAGE_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;

public class CreateDishCategoryFormSubmitCommand implements Command {

    private final DishCategoryService dishCategoryService;

    public CreateDishCategoryFormSubmitCommand(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        DishCategory dishCategory = new DishCategory();
        String categoryName = request.getParameter(CATEGORY_NAME_JSP_PARAM);
        dishCategory.setCategoryName(categoryName);
        dishCategoryService.save(dishCategory);
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "New category has been added to menu");

        return COMMAND_RESULT_MESSAGE_JSP;
    }
}
