package by.training.sokolov.command;

import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.category.service.DishCategoryService;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.dish.service.DishService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.ApplicationModule.DISH_MENU;

public class ViewDishMenuCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        /*
            todo разбить команду на 2 команды: показ категорий и показ меню
        */

        setCategoriesToRequest(request);

        DishService dishService = BeanFactory.getDishService();
        List<Dish> dishes = dishService.findAll();

        List<String> categoryNames = CategoryNameUtil.getCategoryNames(request);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals("all")) {

            request.setAttribute("dishes", dishes);
            return DISH_MENU;
        }

        List<Dish> filteredDishes = new ArrayList<>();
        for (String categoryName : categoryNames) {
            for (Dish dish : dishes) {
                if (dish.getDishCategory().getCategoryName().equals(categoryName)) {
                    filteredDishes.add(dish);
                }
            }
        }

        request.setAttribute("dishes", filteredDishes);

        return DISH_MENU;
    }

    private void setCategoriesToRequest(HttpServletRequest request) throws SQLException {
        DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);
    }

}
