package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.service.OrderItemService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static by.training.sokolov.application.constants.JspName.ORDER_ACCEPTED_JSP;
import static by.training.sokolov.entity.order.constants.OrderStatus.SUBMITTED;

public class OrderCheckoutSubmitCommand implements Command {

    private final OrderItemService orderItemService;
    private final UserOrderService userOrderService;

    public OrderCheckoutSubmitCommand(OrderItemService orderItemService, UserOrderService userOrderService) {
        this.orderItemService = orderItemService;
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {


        String name = request.getParameter("user.name");
        String email = request.getParameter("user.email");
        String phoneNumber = request.getParameter("user.phoneNumber");

        String locality = request.getParameter("order.address.locality");
        String street = request.getParameter("order.address.street");
        String buildingNumber = request.getParameter("order.address.buildingNumber");
        String flatNumber = request.getParameter("order.address.flatNumber");
        String porch = request.getParameter("order.address.porch");
        String floor = request.getParameter("order.address.floor");

        StringBuilder address = new StringBuilder();
        address.append(locality)
                .append(street)
                .append(buildingNumber)
                .append(flatNumber)
                .append(porch)
                .append(floor);

        UserOrder currentOrder = userOrderService.getCurrentUserOrder(request.getSession().getId());
        request.setAttribute("itemList", orderItemService.findAllItemsByOrderId(currentOrder.getId()));

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setCustomDeliveryAddress(new String(address));

        currentOrder.setDeliveryAddress(deliveryAddress);

        /*
        todo доставка еды будет пока что с выбором только
          на сегодня и выбор времени
         */

        String minutesHours = request.getParameter("order.timeOfDelivery");
        String seconds = ":00";
        String dateString = LocalDate.now().toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = formatter.parse(dateString + " " + minutesHours + seconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timestamp timeOfDelivery = new Timestamp(date.getTime());
        currentOrder.setTimeOfDelivery(timeOfDelivery);

        currentOrder.setOrderStatus(SUBMITTED);



        return ORDER_ACCEPTED_JSP;
    }
}
