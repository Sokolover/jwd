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

import static by.training.sokolov.application.constants.JspName.INDEX_JSP;
import static by.training.sokolov.command.constants.CommandReturnValues.BASKET_ADD_ITEM;

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

        UserOrder currentOrder = userOrderService.getCurrentUserOrder(request.getSession().getId());
        if (Objects.isNull(currentOrder)) {
            request.setAttribute("error", "please, create order");
            return INDEX_JSP;
        }
        Long currentUserOrderId = currentOrder.getId();

        String dishIdString = request.getParameter("dish.id");
        Long dishIdLong = Long.parseLong(dishIdString);

        if (Objects.isNull(orderItemService.getFromCurrentOrderByDishId(dishIdLong, currentUserOrderId))) {

            OrderItem orderItem = new OrderItem();
            orderItem.getUserOrder().setId(currentUserOrderId);

            dishIdString = request.getParameter("dish.id");
            dishIdLong = Long.parseLong(dishIdString);
            Dish dish = dishService.getById(dishIdLong);
            orderItem.setDish(dish);

            String dishAmountString = request.getParameter("order.dish.amount");
            Integer dishAmountInteger = Integer.parseInt(dishAmountString);
            orderItem.setDishAmount(dishAmountInteger);

            orderItemService.addNewOrderItem(orderItem);

            return BASKET_ADD_ITEM;
        }

        return BASKET_ADD_ITEM;
    }

}
