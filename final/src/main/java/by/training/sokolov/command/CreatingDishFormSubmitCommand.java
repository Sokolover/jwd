package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.util.PictureEncodingUtil;

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

public class CreatingDishFormSubmitCommand implements Command {

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

        String name = request.getParameter(DISH_NAME_JSP_ATTRIBUTE);

        String costString = request.getParameter(DISH_COST_JSP_ATTRIBUTE);

        long costLong;
        try {
            costLong = Long.parseLong(costString);
        } catch (NumberFormatException e) {
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "Invalid cost format or empty string");
            List<DishCategory> dishCategories = dishCategoryService.findAll();
            request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);
            return DISH_CREATE_FORM_JSP;
        }

        BigDecimal bigDecimalCost = BigDecimal.valueOf(costLong);

        String description = request.getParameter(DISH_DESCRIPTION_JSP_ATTRIBUTE);
        String category = request.getParameter(DISH_CATEGORY_JSP_ATTRIBUTE);

        Part picture = request.getPart(DISH_PICTURE_JSP_ATTRIBUTE);
        String stringPicture;

        try {
            stringPicture = PictureEncodingUtil.getPictureEncoded(picture);
        } catch (IllegalArgumentException e) {
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "You have forgotten to choose a picture, please choose it now");
            List<DishCategory> dishCategories = dishCategoryService.findAll();
            request.setAttribute(CATEGORY_LIST_JSP_ATTRIBUTE, dishCategories);
            return DISH_CREATE_FORM_JSP;
        }

        Dish dish = new Dish();
        dish.setName(name);
        dish.setCost(bigDecimalCost);
        dish.setDescription(description);
        DishCategory dishCategory = new DishCategory();
        dishCategory.setCategoryName(category);
        dish.setDishCategory(dishCategory);
        dish.setPicture(stringPicture);

        dishService.save(dish);

        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "your dish has been created and added to menu");

        return COMMAND_RESULT_MESSAGE_JSP;
    }

}
