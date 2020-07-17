package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.entity.order.constants.OrderStatus.SUBMITTED;

public class OrderCheckoutSubmitCommand implements Command {

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
        /*
        TODO попробовать посменять "user_s".equals(customerName)
            на
            Objects.isNull(customerUsersAddress)
         */
        if ("user_s".equals(customerName)) {
            currentOrder.setCustomerName(user.getName());
        } else {
            currentOrder.setCustomerName(request.getParameter(USER_NAME_JSP_PARAM));
        }

        String customerPhoneNumber = request.getParameter(DEFAULT_USER_PHONE_NUMBER_JSP_PARAM);
        if ("user_s".equals(customerPhoneNumber)) {
            currentOrder.setCustomerPhoneNumber(user.getPhoneNumber());
        } else {
            currentOrder.setCustomerPhoneNumber(request.getParameter(USER_PHONE_NUMBER_JSP_PARAM));
        }

        String customerUsersAddress = request.getParameter(DEFAULT_ORDER_ADDRESS_JSP_PARAM);
        if (Objects.isNull(customerUsersAddress)) {
            setCustomAddress(request, currentOrder);
        }

        setTimeOfDelivery(request, currentOrder);

        currentOrder.setOrderStatus(SUBMITTED);

        userOrderService.update(currentOrder);
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "order accepted");

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private void setTimeOfDelivery(HttpServletRequest request, UserOrder currentOrder) {

        String timeOfDeliveryMinutes = request.getParameter(ORDER_TIME_OF_DELIVERY_JSP_ATTRIBUTE);
        LocalDateTime date = LocalDateTime.parse(timeOfDeliveryMinutes);
        currentOrder.setTimeOfDelivery(date);
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
    }
}
