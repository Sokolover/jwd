package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.deliveryaddress.model.DeliveryAddress;
import by.training.sokolov.order.constants.OrderStatus;
import by.training.sokolov.order.model.UserOrder;
import by.training.sokolov.order.service.UserOrderService;
import by.training.sokolov.user.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class CreateOrderCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(CreateOrderCommand.class.getName());

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

         /*
            УЖЕ СДЕЛАНО: вместо if (!userOrder.getInProgress() && (userOrder.getUser().getId().equals(currentUser.getId())))
                            сделан метод findInProgressUserOrder(Long id) в UserOrderDao
                            и кидать туда currentUser.getId()
         */

        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        UserOrder currentUserOrder = userOrderService.findInProgressUserOrder(currentUser.getId());

        boolean inProgress = currentUserOrder.getInProgress();
        if (!inProgress) {
            createNewOrder(request);
        }

        return "create_order";
    }

    private void createNewOrder(HttpServletRequest request) throws SQLException {

        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrder userOrder = new UserOrder();
        userOrder.getUser().setId(currentUser.getId());

        //todo проверить тестами
//        SimpleDateFormat datetimeFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
//        datetimeFormatter.format(date);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        userOrder.setTimeOfDelivery(timestamp);

        String defaultOrderStatus = OrderStatus.START_TO_PROCESS;
        userOrder.setOrderStatus(defaultOrderStatus);

        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setUserAddress(currentUser.getUserAddress());
        userOrder.setDeliveryAddress(deliveryAddress);

        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        userOrderService.save(userOrder);
    }
}
