package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.util.TimeOfDeliveryGeneratorUtil;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.CHECKOUT_JSP;
import static by.training.sokolov.core.constants.JspName.ERROR_MESSAGE_JSP;

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
        if (Objects.isNull(currentOrder)) {
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "please, create order");
            return ERROR_MESSAGE_JSP;
        }

        List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(currentOrder.getId());
        if (Objects.isNull(orderItems) || orderItems.isEmpty()) {
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "please, add items to your order");
            return ERROR_MESSAGE_JSP;
        }
        request.setAttribute(ORDER_ITEM_LIST_JSP_ATTRIBUTE, orderItems);

        BigDecimal orderCost = userOrderService.getOrderCost(currentOrder);
        request.setAttribute(TOTAL_ORDER_COST_JSP_ATTRIBUTE, orderCost);

//        LocalDateTime localDateTime = LocalDateTime.now();
//        localDateTime.getHour() + localDateTime.getMinute();

        List<LocalDateTime> localDateTimeList = TimeOfDeliveryGeneratorUtil.findTimeVariants();
        request.setAttribute(TIME_LIST_JSP_ATTRIBUTE, localDateTimeList);

        return CHECKOUT_JSP;
    }
}
