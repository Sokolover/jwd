package by.training.sokolov.command.dish;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.service.DishCategoryService;
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
import static by.training.sokolov.core.constants.JspName.DISH_LIST_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class DishMenuDisplayCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DishMenuDisplayCommand.class.getName());

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;

    public DishMenuDisplayCommand(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        JspUtil jspUtil = ApplicationContext.getInstance().getBean(JspUtil.class);
        jspUtil.setCategoriesAttribute(request);

        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);

        // для удержания чекбоксов после их отправки
//        request.setAttribute("selectedCategories", categoryNames);

        String currentPageString = request.getParameter(QUERY_PAGE_PARAM);
        int currentPage;

        if (Objects.isNull(currentPageString) || currentPageString.isEmpty()) {
            currentPage = FIRST_PAGE;
        } else {
            currentPage = Integer.parseInt(currentPageString);
        }

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            request.setAttribute(DISHES_JSP_ATTRIBUTE, dishService.findAll(currentPage, RECORDS_PER_PAGE));
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISHES_JSP_ATTRIBUTE));
            LOGGER.info("All dishes will be shown");

            request.setAttribute(NUMBER_OF_PAGES_JSP_ATTRIBUTE, getNumberOfPages());
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, NUMBER_OF_PAGES_JSP_ATTRIBUTE));

            request.setAttribute(CURRENT_PAGE_JSP_ATTRIBUTE, currentPage);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, CURRENT_PAGE_JSP_ATTRIBUTE));

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

        request.setAttribute(DISHES_JSP_ATTRIBUTE, filteredDishes);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISHES_JSP_ATTRIBUTE));
        LOGGER.info("Filtered dishes will be shown");

        return DISH_LIST_JSP;
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
