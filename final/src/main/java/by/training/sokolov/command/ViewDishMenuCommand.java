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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static by.training.sokolov.ApplicationModule.DISH_MENU;

public class ViewDishMenuCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        /*
            todo разбить команду на 2 команды: показ категорий и показ меню
        */

        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> paramList = new ArrayList<>();
        List<String[]> paramValues = new ArrayList<>();

        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();
            paramValues.add(request.getParameterValues(paramName));
//            paramList.add(paramName);
        }

        List<String> reqValues = new ArrayList<>();
        for (String[] paramValue : paramValues) {
            reqValues.addAll(Arrays.asList(paramValue));
        }

//        String categoryFromRequest = CommandUtil.getCategoryFromRequest(request);
        DishService dishService = BeanFactory.getDishService();
        List<Dish> dishes = dishService.findAll();

        List<Dish> filteredDishes = new ArrayList<>();
        for (String categoryName : reqValues) {
            for (Dish dish : dishes) {
                if (dish.getDishCategory().getCategoryName().equals(categoryName)) {
                    filteredDishes.add(dish);
                }
            }
        }

        /*
        todo сделать чтобы, если в фильтре стоят блюда
            а в списке их нет, то показывать пустоту
            + checkbox который покажет все блюда
         */

        if (filteredDishes.isEmpty()) {
            request.setAttribute("dishes", dishes);
        } else {
            request.setAttribute("dishes", filteredDishes);
        }

        DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);

        return DISH_MENU;
    }
}
