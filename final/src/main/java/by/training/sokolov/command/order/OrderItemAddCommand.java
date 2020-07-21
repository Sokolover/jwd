package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.command.constants.CommandReturnValues.ORDER_ADD_ITEM_RESULT;
import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.ERROR_MESSAGE_JSP;

public class OrderItemAddCommand implements Command {

    private final OrderItemService orderItemService;
    private final DishService dishService;
    private final UserOrderService userOrderService;

    public OrderItemAddCommand(OrderItemService orderItemService, DishService dishService, UserOrderService userOrderService) {
        this.orderItemService = orderItemService;
        this.dishService = dishService;
        this.userOrderService = userOrderService;
    }

    /*
        нельзя добавлять блюдо в заказ если оно уже есть в заказе, можно только удалить
        OrderItem и добавить новый
     */
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder currentOrder = userOrderService.getCurrentUserOrder(sessionId);
        if (Objects.isNull(currentOrder)) {
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "please, create order");
            return ERROR_MESSAGE_JSP;
        }
        Long currentUserOrderId = currentOrder.getId();

        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        Long dishIdLong = Long.parseLong(dishIdString);

        if (Objects.isNull(orderItemService.getFromCurrentOrderByDishId(dishIdLong, currentUserOrderId))) {

            addItemToOrder(request, currentUserOrderId);
        }

        return ORDER_ADD_ITEM_RESULT;
    }

    private void addItemToOrder(HttpServletRequest request, Long currentUserOrderId) throws SQLException, ConnectionException {

        String dishIdString;
        Long dishIdLong;
        OrderItem orderItem = new OrderItem();
        orderItem.getUserOrder().setId(currentUserOrderId);

        dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        dishIdLong = Long.parseLong(dishIdString);
        Dish dish = dishService.getById(dishIdLong);
        orderItem.setDish(dish);

        String dishAmountString = request.getParameter(ORDER_DISH_AMOUNT_JSP_PARAM);
        Integer dishAmountInteger = Integer.parseInt(dishAmountString);
        orderItem.setDishAmount(dishAmountInteger);

        orderItemService.addNewOrderItem(orderItem);
    }

}
