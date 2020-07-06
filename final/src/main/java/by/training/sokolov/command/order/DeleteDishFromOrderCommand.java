package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.orderitem.service.OrderItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static by.training.sokolov.command.constants.CommandReturnValues.DELETE_DISH_FROM_ORDER;

public class DeleteDishFromOrderCommand implements Command {

    private final OrderItemService orderItemService;

    public DeleteDishFromOrderCommand(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String itemIdString = request.getParameter("orderItem.id");
        Long itemIdLong = Long.parseLong(itemIdString);
        orderItemService.deleteById(itemIdLong);

        return DELETE_DISH_FROM_ORDER;
    }

}
