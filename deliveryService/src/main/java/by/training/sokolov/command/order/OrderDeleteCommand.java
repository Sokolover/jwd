package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
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

public class OrderDeleteCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderDeleteCommand.class.getName());

    private final UserOrderService userOrderService;

    public OrderDeleteCommand(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder currentOrder = userOrderService.getCurrentUserOrder(sessionId);

        if (Objects.isNull(currentOrder)) {

            String message = "You haven't created order yet";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.error(message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

            return COMMAND_RESULT_MESSAGE_JSP;
        }

        userOrderService.deleteById(currentOrder.getId());

        String message = "Order has been deleted";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

        return COMMAND_RESULT_MESSAGE_JSP;
    }

}
