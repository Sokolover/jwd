package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.MESSAGE_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;

public class OrderDeleteCommand implements Command {
    /*
todo 1. удалить все orderItems из order, 2. удалить order
 */

    private final UserOrderService userOrderService;

    public OrderDeleteCommand(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder currentOrder = userOrderService.getCurrentUserOrder(sessionId);
        if(Objects.isNull(currentOrder)){
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "You haven't created order yet");
            return COMMAND_RESULT_MESSAGE_JSP;
        }
        userOrderService.deleteById(currentOrder.getId());
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, "Order has been deleted");

        return COMMAND_RESULT_MESSAGE_JSP;
    }


}
