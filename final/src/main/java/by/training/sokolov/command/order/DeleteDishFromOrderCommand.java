package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.core.factory.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class DeleteDishFromOrderCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        String itemIdString = request.getParameter("orderItem.id");
        Long itemIdLong = Long.parseLong(itemIdString);
        BeanFactory.getOrderItemService().deleteById(itemIdLong);

        return "delete_dish_from_order";
    }

}
