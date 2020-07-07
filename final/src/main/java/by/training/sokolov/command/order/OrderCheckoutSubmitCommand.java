package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static by.training.sokolov.application.constants.JspName.ORDER_SUBMITTED_JSP;
import static by.training.sokolov.entity.order.constants.OrderStatus.SUBMITTED;

public class OrderCheckoutSubmitCommand implements Command {

    private final OrderItemService orderItemService;
    private final UserOrderService userOrderService;
    private final UserService userService;

    public OrderCheckoutSubmitCommand(OrderItemService orderItemService, UserOrderService userOrderService, UserService userService) {
        this.orderItemService = orderItemService;
        this.userOrderService = userOrderService;
        this.userService = userService;
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

        /*
        todo доставка еды будет пока что с выбором только
          на сегодня и выбор времени
         */
        /*
        fixme КОГДА ПРИХОДИТ ВРЕМЯ 19:00 ПОЕМУ-ТО В БАЗУ ЗАПИСЫВАЕТСЯ 16:00!!!
         */
        setTimeOfDelivery(request, currentOrder);

        currentOrder.setOrderStatus(SUBMITTED);

        userOrderService.update(currentOrder);

        return ORDER_SUBMITTED_JSP;
    }

    private void setTimeOfDelivery(HttpServletRequest request, UserOrder currentOrder) {
        String timeOfDeliveryMinutes = request.getParameter("order.timeOfDelivery");
//        String seconds = ":00";
        String dateString = LocalDate.now().toString();
//        ISO_LOCAL_DATE_TIME   | 	ISO Local Date and Time	 |  '2011-12-03T10:15:30'
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String FORMATTER_DELIMITER = "T";
        LocalDateTime date = LocalDateTime.parse(dateString + FORMATTER_DELIMITER + timeOfDeliveryMinutes);
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
