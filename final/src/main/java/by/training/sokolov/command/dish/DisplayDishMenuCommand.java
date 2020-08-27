package by.training.sokolov.command.dish;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.util.CategoryNameUtil;
import by.training.sokolov.util.JspUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.DISH_MENU_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class DisplayDishMenuCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DisplayDishMenuCommand.class.getName());

    private final DishService dishService;

    public DisplayDishMenuCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        JspUtil jspUtil = ApplicationContext.getInstance().getBean(JspUtil.class);
        jspUtil.setCategoriesAttribute(request);

        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);

        request.setAttribute(SELECTED_CATEGORIES_JSP_ATTRIBUTE, categoryNames);

        if (isDisplayAllCategories(categoryNames)) {

            return displayAllDishes(request);
        }
        /*
        TODO ПАГИНАЦИЯ на фильтрованные блюда
         */
        return displayFilteredDishes(request, categoryNames);
    }

    private boolean isDisplayAllCategories(List<String> categoryNames) {

        return categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES);
    }

    private String displayFilteredDishes(HttpServletRequest request, List<String> categoryNames) throws ConnectionException, SQLException {

        List<Dish> filteredDishes = new ArrayList<>();
        for (String categoryName : categoryNames) {
            List<Dish> dishes = dishService.getByCategory(categoryName);
            if (dishes.isEmpty()) {
                continue;
            }
            filteredDishes.addAll(dishes);
        }

        request.setAttribute(DISHES_JSP_ATTRIBUTE, filteredDishes);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISHES_JSP_ATTRIBUTE, filteredDishes.toString()));
        LOGGER.info("Filtered dishes will be shown");

        return DISH_MENU_JSP;
    }

    private String displayAllDishes(HttpServletRequest request) throws SQLException, ConnectionException {

        String currentPageString = request.getParameter(QUERY_PARAM_PAGE);

        int currentPage;

        if (Objects.isNull(currentPageString) || currentPageString.isEmpty()) {
            currentPage = FIRST_PAGE;
        } else {
            currentPage = Integer.parseInt(currentPageString);
        }

        List<Dish> all = dishService.findAll(currentPage, RECORDS_PER_PAGE);
        request.setAttribute(DISHES_JSP_ATTRIBUTE, all);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISHES_JSP_ATTRIBUTE, all));
        LOGGER.info("All dishes will be shown");

        int numberOfPages = getNumberOfPages();
        request.setAttribute(NUMBER_OF_PAGES_JSP_ATTRIBUTE, numberOfPages);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, NUMBER_OF_PAGES_JSP_ATTRIBUTE, numberOfPages));

        request.setAttribute(CURRENT_PAGE_JSP_ATTRIBUTE, currentPage);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, CURRENT_PAGE_JSP_ATTRIBUTE, currentPage));

        return DISH_MENU_JSP;
    }

    private int getNumberOfPages() throws ConnectionException, SQLException {

        int rows = dishService.getNumberOfRows();
        int numberOfPages = rows / RECORDS_PER_PAGE;
        if (numberOfPages % RECORDS_PER_PAGE > 0) {

            numberOfPages++;
        }
        return numberOfPages;
    }

}
