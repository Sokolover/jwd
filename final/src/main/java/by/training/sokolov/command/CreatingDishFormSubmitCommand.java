package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;

public class CreatingDishFormSubmitCommand implements Command {

    private final DishService dishService;

    public CreatingDishFormSubmitCommand(DishService dishService) {
        this.dishService = dishService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String name = request.getParameter(DISH_NAME_JSP_ATTRIBUTE);
        String cost = request.getParameter(DISH_COST_JSP_ATTRIBUTE);
        BigDecimal bigDecimalCost = BigDecimal.valueOf(Long.parseLong(cost));
        String description = request.getParameter(DISH_DESCRIPTION_JSP_ATTRIBUTE);
        String category = request.getParameter(DISH_CATEGORY_JSP_ATTRIBUTE);

        Part picture = request.getPart(DISH_PICTURE_JSP_ATTRIBUTE);
        String stringPicture = getPictureEncoded(picture);

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

    private String getPictureEncoded(Part picture) throws IOException {

        String fileName = getFileName(picture);
        String filePath = TMP_DIR + File.separator + fileName;
        picture.write(filePath);
        File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private String getFileName(Part part) {

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
