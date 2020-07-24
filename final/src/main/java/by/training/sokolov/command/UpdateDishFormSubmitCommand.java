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

        /*
        todo сделать здесь простую валидацию, но с помощью аннотаций UpdateDishFormSubmitCommand
         */

        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        Long dishIdLong = Long.parseLong(dishIdString);
        Dish dish = dishService.getById(dishIdLong);

        updateDishName(request, dish);
        if (updateDishCost(request, dish)) {
            return DISH_CREATE_FORM_JSP;
        }
        updateDishDescription(request, dish);
        updateDishCategory(request, dish);
        updateDishPicture(request, dish);

        dishService.update(dish);

        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "Your dish has been updated!");
        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private void updateDishPicture(HttpServletRequest request, Dish dish) throws IOException, ServletException {

        Part picture = request.getPart(DISH_PICTURE_JSP_ATTRIBUTE);

        try {
            String stringPicture = PictureEncodingUtil.getPictureEncoded(picture);
            dish.setPicture(stringPicture);
        } catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private void updateDishCategory(HttpServletRequest request, Dish dish) {

        String category = request.getParameter(DISH_CATEGORY_JSP_ATTRIBUTE);
        if (!category.trim().isEmpty()) {

            DishCategory dishCategory = new DishCategory();
            dishCategory.setCategoryName(category);
            dish.setDishCategory(dishCategory);
        }
    }

    private void updateDishDescription(HttpServletRequest request, Dish dish) {

        String description = request.getParameter(DISH_DESCRIPTION_JSP_ATTRIBUTE);
        if (!description.trim().isEmpty()) {

            dish.setDescription(description);
        }
    }

    private boolean updateDishCost(HttpServletRequest request, Dish dish) throws SQLException, ConnectionException {

        String costString = request.getParameter(DISH_COST_JSP_ATTRIBUTE);
        if (!costString.trim().isEmpty()) {

            long costLong;
            try {

                costLong = Long.parseLong(costString);
            } catch (Exception e) {

                request.setAttribute(ERROR_JSP_ATTRIBUTE, "Invalid cost format");
                List<DishCategory> dishCategories = dishCategoryService.findAll();
                request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);
                return true;
            }

            BigDecimal bigDecimalCost = BigDecimal.valueOf(costLong);
            dish.setCost(bigDecimalCost);
        }
        return false;
    }

    private void updateDishName(HttpServletRequest request, Dish dish) {

        String name = request.getParameter(DISH_NAME_JSP_ATTRIBUTE);
        if (!name.trim().isEmpty()) {

            dish.setName(name);
        }
    }

}
