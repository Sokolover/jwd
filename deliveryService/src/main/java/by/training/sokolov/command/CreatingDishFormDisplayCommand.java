package by.training.sokolov.command;

import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.util.JspUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.JspName.CREATE_DISH_FORM_JSP;

public class CreatingDishFormDisplayCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreatingDishFormDisplayCommand.class.getName());

    private final DishCategoryService dishCategoryService;

    public CreatingDishFormDisplayCommand(DishCategoryService dishCategoryService) {
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        JspUtil.setCategoriesAttribute(request, dishCategoryService);
        LOGGER.info("Command have been processed");

        return CREATE_DISH_FORM_JSP;
    }
}
