package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.DISH_UPDATE_FORM_JSP;

public class UpdateDishFormDisplayCommand implements Command {

    private final DishCategoryService dishCategoryService;
    private final DishService dishService;

    public UpdateDishFormDisplayCommand(DishCategoryService dishCategoryService, DishService dishService) {
        this.dishCategoryService = dishCategoryService;
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        List<DishCategory> dishCategories = dishCategoryService.findAll();
        request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);

        String dishId = request.getParameter(DISH_ID_JSP_PARAM);
        Long dishIdLong = Long.parseLong(dishId);
        Dish dish = dishService.getById(dishIdLong);
        request.setAttribute(DISH_JSP_ATTRIBUTE, dish);

        return DISH_UPDATE_FORM_JSP;
    }
}
