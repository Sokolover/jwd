package by.training.sokolov.util;

import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.Objects.isNull;

public class JspUtil {

    private static final Logger LOGGER = Logger.getLogger(JspUtil.class.getName());

    private final DishCategoryService dishCategoryService;
    private final DishService dishService;

    public JspUtil(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    public void setDishAttributeByDishParam(HttpServletRequest request) throws SQLException, ConnectionException {

        String dishId = request.getParameter(DISH_ID_JSP_PARAM);
        if (isNull(dishId)) {
            return;
        }
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_ID_JSP_PARAM, dishId));

        Dish dish = dishService.getById(parseLong(dishId));

        request.setAttribute(DISH_JSP_ATTRIBUTE, dish);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, DISH_JSP_ATTRIBUTE, dish.toString()));
    }

    public void setCategoriesAttribute(HttpServletRequest request) throws SQLException, ConnectionException {

        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, categories);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, CATEGORY_LIST_JSP_ATTRIBUTE, categories.toString()));
    }
}
