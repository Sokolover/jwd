package by.training.sokolov.command.dish;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.util.JspUtil;
import by.training.sokolov.util.PictureEncodingUtil;
import by.training.sokolov.validation.BeanValidator;
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
import static by.training.sokolov.core.constants.JspName.UPDATE_DISH_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.Long.parseLong;
import static java.lang.String.format;

public class UpdateDishFormSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(UpdateDishFormSubmitCommand.class.getName());

    private final DishService dishService;
    private final DishCategoryService dishCategoryService;

    public UpdateDishFormSubmitCommand(DishService dishService, DishCategoryService dishCategoryService) {
        this.dishService = dishService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_ID_JSP_PARAM, dishIdString));

        Dish dish = dishService.getById(parseLong(dishIdString));

        setDishPicture(request, dish);

        updateDishName(request, dish);
        if (!updateDishCost(request, dish)) {
            return UPDATE_DISH_FORM_JSP;
        }
        updateDishDescription(request, dish);
        updateDishCategory(request, dish);
        updateDishPicture(request, dish);

        dishService.update(dish);

        String message = "Your dish has been updated!";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private void updateDishPicture(HttpServletRequest request, Dish dish) throws IOException, ServletException {

        Part picture = request.getPart(DISH_PICTURE_JSP_PARAM);

        try {

            String stringPicture = PictureEncodingUtil.getPictureEncoded(picture);
            dish.setPicture(stringPicture);
            LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_PICTURE_JSP_PARAM, stringPicture.substring(0, 20)));
            LOGGER.info("Dish picture has been updated");

        } catch (IllegalArgumentException e) {

            LOGGER.info("Dish picture hasn't been updated");
        }
    }

    private void updateDishCategory(HttpServletRequest request, Dish dish) {

        String category = request.getParameter(DISH_CATEGORY_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_CATEGORY_NAME_JSP_PARAM, category));

        if (!category.trim().isEmpty()) {

            DishCategory dishCategory = new DishCategory();
            dishCategory.setCategoryName(category);
            dish.setDishCategory(dishCategory);
        }
    }

    private void updateDishDescription(HttpServletRequest request, Dish dish) {

        String description = request.getParameter(DISH_DESCRIPTION_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_DESCRIPTION_JSP_PARAM, description));

        if (!description.trim().isEmpty()) {

            dish.setDescription(description);
        }
    }

    private boolean updateDishCost(HttpServletRequest request, Dish dish) throws SQLException, ConnectionException {

        String costString = request.getParameter(DISH_COST_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_COST_JSP_PARAM, costString));

        if (!costString.trim().isEmpty()) {

            BigDecimal bigDecimalCost;
            try {

                bigDecimalCost = new BigDecimal(costString);
            } catch (NumberFormatException e) {

                request.setAttribute(ERROR_JSP_ATTRIBUTE, "Invalid cost format");
                List<DishCategory> dishCategories = dishCategoryService.findAll();
                request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);
                return false;
            }

            dish.setCost(bigDecimalCost);
        }
        return true;
    }

    private void updateDishName(HttpServletRequest request, Dish dish) {

        String name = request.getParameter(DISH_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_NAME_JSP_PARAM, name));

        if (!name.trim().isEmpty()) {

            dish.setName(name);
        }
    }

    private void setDishPicture(HttpServletRequest request, Dish dish) throws IOException, ServletException, ConnectionException, SQLException {

        Part picture = request.getPart(DISH_PICTURE_JSP_PARAM);

        try {

            String stringPicture = PictureEncodingUtil.getPictureEncoded(picture);
            dish.setPicture(stringPicture);
            LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_PICTURE_JSP_PARAM, stringPicture.substring(0, 20)));
            LOGGER.info("Dish picture has been uploaded");

        } catch (IllegalArgumentException e) {

            String message = "Dish picture hasn't been uploaded";
            LOGGER.info(message);
        }
    }

    private String createReturnAnswer(HttpServletRequest request, String message) throws SQLException, ConnectionException {

        request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));
        LOGGER.error(message);

        JspUtil jspUtil = ApplicationContext.getInstance().getBean(JspUtil.class);

        jspUtil.setCategoriesAttribute(request);
        jspUtil.setDishAttributeByDishParam(request);

        return UPDATE_DISH_FORM_JSP;
    }
}
