package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.DISH_CATEGORY_JSP;
import static by.training.sokolov.core.constants.JspName.DISH_LIST_JSP;

public class ViewDishMenuCommand implements Command {

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;

    public ViewDishMenuCommand(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        setCategoriesToRequest(request);
        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);
        request.setAttribute("selectedCategories", categoryNames);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            request.setAttribute(DISHES_JSP_ATTRIBUTE, dishService.findAll());
            return DISH_LIST_JSP;
        }

        List<Dish> filteredDishes = new ArrayList<>();
        for (String categoryName : categoryNames) {
            filteredDishes.addAll(dishService.getByCategory(categoryName));
        }

        request.setAttribute(DISHES_JSP_ATTRIBUTE, filteredDishes);

        return DISH_LIST_JSP;
    }

    private void setCategoriesToRequest(HttpServletRequest request) throws SQLException, ConnectionException {
//        request.setAttribute(CATEGORY_JSP_ATTRIBUTE, DISH_CATEGORY_JSP);

        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, categories);
    }
}
