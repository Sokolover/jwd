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
import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.ValidationResult;
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
import static by.training.sokolov.core.constants.JspName.CREATE_DISH_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static by.training.sokolov.validation.CreateMessageUtil.createMessage;
import static java.lang.String.format;

public class CreateDishFormSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateDishFormSubmitCommand.class.getName());

    private final DishService dishService;
    //    private final DishCategoryService dishCategoryService;
    private final BeanValidator validator;

    public CreateDishFormSubmitCommand(DishService dishService, DishCategoryService dishCategoryService, BeanValidator validator) {
        this.dishService = dishService;
//        this.dishCategoryService = dishCategoryService;
        this.validator = validator;
    }


    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String name = request.getParameter(DISH_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_NAME_JSP_PARAM, name));

        List<Dish> dishes = dishService.findAll();

        for (Dish dish : dishes) {

            if (dish.getName().equalsIgnoreCase(name)) {

                String message = "Please, choose another name, dish with this name is already exist";
                request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                LOGGER.error(message);

                return createReturnAnswer(request, message);
            }
        }

        String costString = request.getParameter(DISH_COST_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_COST_JSP_PARAM, costString));

        BigDecimal bigDecimalCost;
        try {

            bigDecimalCost = new BigDecimal(costString);
        } catch (NumberFormatException e) {

            String message = "Invalid cost format or empty cost field";
            return createReturnAnswer(request, message);
        }

        String description = request.getParameter(DISH_DESCRIPTION_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_DESCRIPTION_JSP_PARAM, description));

        String categoryName = request.getParameter(DISH_CATEGORY_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_CATEGORY_NAME_JSP_PARAM, categoryName));

        Dish dish = new Dish();
        dish.setName(name);
        dish.setCost(bigDecimalCost);
        dish.setDescription(description);
        DishCategory dishCategory = new DishCategory();
        dishCategory.setCategoryName(categoryName);
        dish.setDishCategory(dishCategory);
        setDishPicture(request, dish);

        return validateFields(request, dish);
    }

    private String validateFields(HttpServletRequest request, Dish dish) throws SQLException, ConnectionException {

        ValidationResult validationResult = validator.validate(dish);
        List<BrokenField> brokenFields = validationResult.getBrokenFields();

        if (brokenFields.isEmpty()) {

            dishService.save(dish);

            String message = "Your dish has been created and added to menu";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

            return COMMAND_RESULT_MESSAGE_JSP;

        } else {

            String message = createMessage(brokenFields);

            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.error(message);

            return createReturnAnswer(request, message);
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

        return CREATE_DISH_FORM_JSP;
    }

}
