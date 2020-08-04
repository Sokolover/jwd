package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.CommonAppConstants.MESSAGE_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.CommonAppConstants.ORDER_ITEM_ID_JSP_PARAM;
import static by.training.sokolov.core.constants.JspName.ORDER_ITEM_LIST_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static java.lang.Long.parseLong;
import static java.lang.String.format;

public class DeleteOrderItemCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteOrderItemCommand.class.getName());

    private final OrderItemService orderItemService;

    public DeleteOrderItemCommand(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String itemIdString = request.getParameter(ORDER_ITEM_ID_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, ORDER_ITEM_ID_JSP_PARAM, itemIdString));

        orderItemService.deleteById(parseLong(itemIdString));

        String message = "Item has been deleted from order";
        request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, MESSAGE_JSP_ATTRIBUTE, message));

        return ORDER_ITEM_LIST_JSP;
    }

}
