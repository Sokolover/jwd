package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.DISH_UPDATE_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.Long.parseLong;
import static java.lang.String.format;

public class UpdateDishFormDisplayCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(UpdateDishFormDisplayCommand.class.getName());

    private final DishCategoryService dishCategoryService;
    private final DishService dishService;

    public UpdateDishFormDisplayCommand(DishCategoryService dishCategoryService, DishService dishService) {
        this.dishCategoryService = dishCategoryService;
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        JspUtil.setCategoriesAttribute(request, dishCategoryService);
        JspUtil.setDishAttributeByDishParam(request, dishService);
        LOGGER.info("Command have been processed");

        return DISH_UPDATE_FORM_JSP;
    }
}
