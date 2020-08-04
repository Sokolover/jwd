package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.dish.model.Dish;
import by.training.sokolov.entity.dish.service.DishService;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.ORDER_ITEM_LIST_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.String.format;
import static java.util.Objects.isNull;

public class AddOrderItemCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddOrderItemCommand.class.getName());

    private final OrderItemService orderItemService;
    private final DishService dishService;
    private final UserOrderService userOrderService;

    public AddOrderItemCommand(OrderItemService orderItemService, DishService dishService, UserOrderService userOrderService) {
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
        UserOrder currentOrder = userOrderService.getBuildingUpUserOrder(sessionId);

        Long currentUserOrderId = currentOrder.getId();
        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_ID_JSP_PARAM, dishIdString));
        Long dishIdLong = Long.parseLong(dishIdString);

        if (isNull(orderItemService.getFromCurrentOrderByDishId(dishIdLong, currentUserOrderId))) {

            addItemToOrder(request, currentUserOrderId);
            String message = "New item added to order";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, MESSAGE_JSP_ATTRIBUTE, message));

        } else {

            String message = "This item is already in the order. If you want to change dish amount - delete correspond item and add it one more time";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, MESSAGE_JSP_ATTRIBUTE, message));
        }

        return ORDER_ITEM_LIST_JSP;
    }

    private void addItemToOrder(HttpServletRequest request, Long currentUserOrderId) throws SQLException, ConnectionException {

        OrderItem orderItem = new OrderItem();
        orderItem.setUserOrderId(currentUserOrderId);

        String dishIdString = request.getParameter(DISH_ID_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DISH_ID_JSP_PARAM, dishIdString));
        Long dishIdLong = Long.parseLong(dishIdString);
        Dish dish = dishService.getById(dishIdLong);
        orderItem.setDish(dish);

        String dishAmountString = request.getParameter(ORDER_DISH_AMOUNT_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, ORDER_DISH_AMOUNT_JSP_PARAM, dishAmountString));
        Integer dishAmountInteger = Integer.parseInt(dishAmountString);
        orderItem.setDishAmount(dishAmountInteger);

        orderItemService.addNewOrderItem(orderItem);
    }

}
