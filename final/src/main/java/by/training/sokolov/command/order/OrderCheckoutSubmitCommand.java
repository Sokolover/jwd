package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.*;
import static by.training.sokolov.entity.order.constants.OrderStatus.SUBMITTED;
import static java.lang.String.format;

public class OrderCheckoutSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderCheckoutSubmitCommand.class.getName());
    private static final String USERS_PARAM_TO_CURRENT_ORDER = "User's [%s] has been set to current order";
    private static final String CUSTOM_PARAM_TO_CURRENT_ORDER = "Custom [%s] has been set to current order";
    private final UserOrderService userOrderService;

    public OrderCheckoutSubmitCommand(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder currentOrder = userOrderService.getCurrentUserOrder(sessionId);
        User user = SecurityContext.getInstance().getCurrentUser(sessionId);

        String customerName = request.getParameter(DEFAULT_USER_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DEFAULT_USER_NAME_JSP_PARAM, customerName));

        /*
        TODO попробовать поменять "user_s".equals(customerName)
            на
            Objects.isNull(customerUsersAddress)
         */

        if ("user_s".equals(customerName)) {
            currentOrder.setCustomerName(user.getName());
            LOGGER.info(format(USERS_PARAM_TO_CURRENT_ORDER, "user name"));
        } else {
            currentOrder.setCustomerName(request.getParameter(USER_NAME_JSP_PARAM));
            LOGGER.info(format(CUSTOM_PARAM_TO_CURRENT_ORDER, "user name"));
        }

        String customerPhoneNumber = request.getParameter(DEFAULT_USER_PHONE_NUMBER_JSP_PARAM);
        if ("user_s".equals(customerPhoneNumber)) {
            currentOrder.setCustomerPhoneNumber(user.getPhoneNumber());
            LOGGER.info(format(USERS_PARAM_TO_CURRENT_ORDER, "phone number"));
        } else {
            currentOrder.setCustomerPhoneNumber(request.getParameter(USER_PHONE_NUMBER_JSP_PARAM));
            LOGGER.info(format(CUSTOM_PARAM_TO_CURRENT_ORDER, "phone number"));
        }

        String customerUsersAddress = request.getParameter(DEFAULT_ORDER_ADDRESS_JSP_PARAM);
        if (Objects.isNull(customerUsersAddress)) {
            setCustomAddress(request, currentOrder);
            LOGGER.info(format(CUSTOM_PARAM_TO_CURRENT_ORDER, "user address"));
        }

        setTimeOfDelivery(request, currentOrder);

        currentOrder.setOrderStatus(SUBMITTED);
        LOGGER.info(format("Order status [%s] has been set to current order", SUBMITTED));

        userOrderService.update(currentOrder);

        String message = "Order accepted";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private void setTimeOfDelivery(HttpServletRequest request, UserOrder currentOrder) {

        String timeOfDeliveryMinutes = request.getParameter(ORDER_TIME_OF_DELIVERY_JSP_ATTRIBUTE);
        LocalDateTime date = LocalDateTime.parse(timeOfDeliveryMinutes);
        currentOrder.setTimeOfDelivery(date);

        LOGGER.info(format(FROM_JSP_ATTR_SET_TO_CURRENT_ORDER_MESSAGE, ORDER_TIME_OF_DELIVERY_JSP_ATTRIBUTE, currentOrder.getId()));
    }

    private void setCustomAddress(HttpServletRequest request, UserOrder currentOrder) {

        String locality = request.getParameter(ORDER_ADDRESS_LOCALITY_JSP_ATTRIBUTE);
        String street = request.getParameter(ORDER_ADDRESS_STREET_JSP_ATTRIBUTE);
        String buildingNumber = request.getParameter(ORDER_ADDRESS_BUILDING_NUMBER_JSP_ATTRIBUTE);
        String flatNumber = request.getParameter(ORDER_ADDRESS_FLAT_NUMBER_JSP_ATTRIBUTE);
        String porch = request.getParameter(ORDER_ADDRESS_PORCH_NUMBER_JSP_ATTRIBUTE);
        String floor = request.getParameter(ORDER_ADDRESS_FLOOR_NUMBER_JSP_ATTRIBUTE);

        StringBuilder address = new StringBuilder();
        address.append(locality)
                .append(",")
                .append(street)
                .append(",")
                .append(buildingNumber)
                .append(",")
                .append(flatNumber)
                .append(",")
                .append(porch)
                .append(",")
                .append(floor);

        currentOrder.getDeliveryAddress().setCustomDeliveryAddress(new String(address));

        LOGGER.info(format(FROM_JSP_ATTR_SET_TO_CURRENT_ORDER_MESSAGE, "Custom address", currentOrder.getId()));
    }
}
