package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.wallet.WalletCommandUtil;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import by.training.sokolov.util.TimeOfDeliveryGeneratorUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.ORDER_CHECKOUT_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class DisplayOrderCheckoutCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DisplayOrderCheckoutCommand.class.getName());

    private final OrderItemService orderItemService;
    private final UserOrderService userOrderService;

    public DisplayOrderCheckoutCommand(OrderItemService orderItemService, UserOrderService userOrderService) {
        this.orderItemService = orderItemService;
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        UserOrder currentOrder = userOrderService.getBuildingUpUserOrder(request.getSession().getId());

        WalletCommandUtil.setCurrentWalletMoneyAmountToRequest(request);

        List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(currentOrder.getId());
        if (Objects.isNull(orderItems) || orderItems.isEmpty()) {
            String message = "Please add items to your order";
            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.error(message);
            return COMMAND_RESULT_MESSAGE_JSP;
        }

        request.setAttribute(ORDER_ITEM_LIST_JSP_ATTRIBUTE, orderItems);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, ORDER_ITEM_LIST_JSP_ATTRIBUTE, orderItems.toString()));

        BigDecimal orderCost = userOrderService.getOrderCost(currentOrder);
        request.setAttribute(TOTAL_ORDER_COST_JSP_ATTRIBUTE, orderCost);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, TOTAL_ORDER_COST_JSP_ATTRIBUTE, orderCost));

        List<LocalDateTime> localDateTimeList = TimeOfDeliveryGeneratorUtil.findTimeVariants();
        request.setAttribute(TIME_LIST_JSP_ATTRIBUTE, localDateTimeList);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, TIME_LIST_JSP_ATTRIBUTE, localDateTimeList.toString()));

        return ORDER_CHECKOUT_FORM_JSP;
    }
}
