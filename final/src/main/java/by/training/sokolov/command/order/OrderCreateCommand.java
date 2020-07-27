package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.order.constants.OrderStatus;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.user.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.MESSAGE_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class OrderCreateCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderCreateCommand.class.getName());

    private final UserOrderService userOrderService;

    public OrderCreateCommand(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        UserOrder currentUserOrder = userOrderService.getCurrentUserOrder(request.getSession().getId());

        if (Objects.isNull(currentUserOrder) || !currentUserOrder.getOrderStatus().equals(OrderStatus.BUILD_UP)) {

            String currentSessionId = request.getSession().getId();
            User user = SecurityContext.getInstance().getCurrentUser(currentSessionId);
            userOrderService.createNewOrder(user);

            String message = "Order has been created now";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));


        } else {

            String message = "Order is already exist";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        }

        return COMMAND_RESULT_MESSAGE_JSP;
    }
}
