package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.DISH_LIST_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class ViewDishMenuCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ViewDishMenuCommand.class.getName());

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;

    public ViewDishMenuCommand(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);

//        request.setAttribute("selectedCategories", categoryNames);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            request.setAttribute(DISHES_JSP_ATTRIBUTE, dishService.findAll());
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISHES_JSP_ATTRIBUTE));
            LOGGER.info("All dishes will be shown");

            return DISH_LIST_JSP;
        }

        List<Dish> filteredDishes = new ArrayList<>();
        for (String categoryName : categoryNames) {
            List<Dish> dishes = dishService.getByCategory(categoryName);
            if (dishes.isEmpty()) {
                continue;
            }
            filteredDishes.addAll(dishes);
        }

        JspUtil.setCategoriesAttribute(request, dishCategoryService);

        request.setAttribute(DISHES_JSP_ATTRIBUTE, filteredDishes);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISHES_JSP_ATTRIBUTE));
        LOGGER.info("Filtered dishes will be shown");

        return DISH_LIST_JSP;
    }

}
