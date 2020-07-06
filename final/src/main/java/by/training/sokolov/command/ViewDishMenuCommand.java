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

import static by.training.sokolov.command.constants.CommandReturnValues.DISH_MENU_DISPLAY;

public class ViewDishMenuCommand implements Command {

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;

    public ViewDishMenuCommand(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        /*
            todo разбить команду на 2 команды: показ категорий и показ меню
        */

        setCategoriesToRequest(request);
        List<String> categoryNames = CategoryNameUtil.getCategoryNames(request);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            request.setAttribute("dishes", dishService.findAll());
            return DISH_MENU_DISPLAY;
        }

        List<Dish> filteredDishes = new ArrayList<>();
        for (String categoryName : categoryNames) {
            filteredDishes.addAll(dishService.getByCategory(categoryName));
        }

        request.setAttribute("dishes", filteredDishes);

        return DISH_MENU_DISPLAY;
    }

    private void setCategoriesToRequest(HttpServletRequest request) throws SQLException, ConnectionException {
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);
    }
}
