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

import static by.training.sokolov.application.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
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

        String customerName = request.getParameter("default.user.name");
        if ("users".equals(customerName)) {
            currentOrder.setCustomerName(user.getName());
        } else {
            currentOrder.setCustomerName(request.getParameter("user.name"));
        }

        String customerPhoneNumber = request.getParameter("default.user.phoneNumber");
        if ("users".equals(customerPhoneNumber)) {
            currentOrder.setCustomerPhoneNumber(user.getPhoneNumber());
        } else {
            currentOrder.setCustomerPhoneNumber(request.getParameter("user.phoneNumber"));
        }

        String customerUsersAddress = request.getParameter("default.order.address");
        if (Objects.isNull(customerUsersAddress)) {
            setCustomAddress(request, currentOrder);
        }

        setTimeOfDelivery(request, currentOrder);

        currentOrder.setOrderStatus(SUBMITTED);

        userOrderService.update(currentOrder);
        request.setAttribute("message", "order accepted");

        return COMMAND_RESULT_MESSAGE_JSP;
    }

    private void setTimeOfDelivery(HttpServletRequest request, UserOrder currentOrder) {

        String timeOfDeliveryMinutes = request.getParameter("order.timeOfDelivery");
        LocalDateTime date = LocalDateTime.parse(timeOfDeliveryMinutes);
        currentOrder.setTimeOfDelivery(date);
    }

    private void setCustomAddress(HttpServletRequest request, UserOrder currentOrder) {

        String locality = request.getParameter("order.address.locality");
        String street = request.getParameter("order.address.street");
        String buildingNumber = request.getParameter("order.address.buildingNumber");
        String flatNumber = request.getParameter("order.address.flatNumber");
        String porch = request.getParameter("order.address.porch");
        String floor = request.getParameter("order.address.floor");

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
