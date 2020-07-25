package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.util.PictureEncodingUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.DISH_CREATE_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.String.format;

public class CreatingDishFormSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreatingDishFormSubmitCommand.class.getName());

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;

    public CreatingDishFormSubmitCommand(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        /*
        todo сделать здесь простую валидацию, но с помощью аннотаций CreatingDishFormSubmitCommand
         */

        String name = request.getParameter(DISH_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_NAME_JSP_PARAM, name));

        String costString = request.getParameter(DISH_COST_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_COST_JSP_PARAM, costString));

        if (costString.length() > 2) {

            return createReturnAnswer(request, "Cost must be two-digit number");
        }

        long costLong;
        try {

            costLong = Long.parseLong(costString);
        } catch (NumberFormatException e) {

            String message = "Invalid cost format or empty string";
            return createReturnAnswer(request, message);
        }

        if (costLong < 0L) {

            return createReturnAnswer(request, "Cost can't have negative value");
        }

        BigDecimal bigDecimalCost = BigDecimal.valueOf(costLong);

        String description = request.getParameter(DISH_DESCRIPTION_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_DESCRIPTION_JSP_PARAM, description));

        String categoryName = request.getParameter(DISH_CATEGORY_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_CATEGORY_NAME_JSP_PARAM, categoryName));

        Part picture = request.getPart(DISH_PICTURE_JSP_PARAM);
        String stringPicture;
        try {
            stringPicture = PictureEncodingUtil.getPictureEncoded(picture);
        } catch (IllegalArgumentException e) {
            String message = "You have forgotten to choose a picture, please choose it now";
            LOGGER.error(message);
            return createReturnAnswer(request, message);
        }
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_PICTURE_JSP_PARAM, stringPicture.substring(0, 20)));

        Dish dish = new Dish();
        dish.setName(name);
        dish.setCost(bigDecimalCost);
        dish.setDescription(description);
        DishCategory dishCategory = new DishCategory();
        dishCategory.setCategoryName(categoryName);
        dish.setDishCategory(dishCategory);
        dish.setPicture(stringPicture);

        dishService.save(dish);

        String message = "Your dish has been created and added to menu";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private String createReturnAnswer(HttpServletRequest request, String message) throws SQLException, ConnectionException {

        request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));
        LOGGER.error(message);

        JspUtil.setCategoriesAttribute(request, dishCategoryService);

        return DISH_CREATE_FORM_JSP;
    }

}
