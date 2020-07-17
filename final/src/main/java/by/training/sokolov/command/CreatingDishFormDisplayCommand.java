package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.CATEGORY_LIST_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.DISH_CREATE_FORM_JSP;

public class CreatingDishFormDisplayCommand implements Command {

    private final DishCategoryService dishCategoryService;

    public CreatingDishFormDisplayCommand(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        List<DishCategory> dishCategories = dishCategoryService.findAll();
        request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);

        return DISH_CREATE_FORM_JSP;
    }
}
