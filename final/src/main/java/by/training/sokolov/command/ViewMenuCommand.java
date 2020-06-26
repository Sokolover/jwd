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

public class ViewMenuCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        /*
        todo
            1. посылать по нажатию кнопки _category1=p, _category2=b ...
            2. CommandUtil.getCategoryFromRequest(request) возвращает список категорий, которые потом будут участвовать
            в фильтрации
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

        //todo сделать чтобы фильтровал + кнопку которая покажет все блюда
        if (filteredDishes.isEmpty()) {
            request.setAttribute("dishList", dishes);
        } else {
            request.setAttribute("dishList", filteredDishes);
        }

        DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);

        return DISH_MENU;
    }
}
