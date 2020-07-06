package by.training.sokolov.command.order;

import by.training.sokolov.application.constants.JspConstants;
import by.training.sokolov.command.Command;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static by.training.sokolov.application.constants.JspName.CHECKOUT_JSP;
import static by.training.sokolov.application.constants.JspName.ORDER_ACCEPTED_JSP;

public class OrderCheckoutDisplayCommand implements Command {

    private final OrderItemService orderItemService;
    private final UserOrderService userOrderService;

    public OrderCheckoutDisplayCommand(OrderItemService orderItemService, UserOrderService userOrderService) {
        this.orderItemService = orderItemService;
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        UserOrder currentOrder = userOrderService.getCurrentUserOrder(request.getSession().getId());
        request.setAttribute("itemList", orderItemService.findAllItemsByOrderId(currentOrder.getId()));

        BigDecimal orderCost = userOrderService.getOrderCost(currentOrder);
        request.setAttribute("totalCost", orderCost);

        return CHECKOUT_JSP;
    }
}
