package by.training.sokolov.command.order;

import by.training.sokolov.command.CategoryNameUtil;
import by.training.sokolov.command.Command;
import by.training.sokolov.command.JspUtil;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.ERROR_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.CommonAppConstants.ORDER_ITEM_LIST_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.ERROR_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.ORDER_ITEM_LIST_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class OrderItemListDisplayCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderItemListDisplayCommand.class.getName());

    private final UserOrderService userOrderService;
    private final OrderItemService orderItemService;
    private final DishCategoryService dishCategoryService;

    public OrderItemListDisplayCommand(UserOrderService userOrderService, OrderItemService orderItemService, DishCategoryService dishCategoryService) {
        this.userOrderService = userOrderService;
        this.orderItemService = orderItemService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder userOrder = userOrderService.getCurrentUserOrder(sessionId);

        if (Objects.isNull(userOrder)) {

            String message = "Please, create order or login (session timeout)";
            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.error(message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

            return ERROR_MESSAGE_JSP;
        }

        JspUtil.setCategoriesAttribute(request, dishCategoryService);
        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(userOrder.getId());
            request.setAttribute(ORDER_ITEM_LIST_JSP_ATTRIBUTE, orderItems);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, ORDER_ITEM_LIST_JSP_ATTRIBUTE));
            LOGGER.info("All items will be shown");

            return ORDER_ITEM_LIST_JSP;
        }

        List<OrderItem> filteredOrderItems = new ArrayList<>();
        for (String categoryName : categoryNames) {
            List<OrderItem> orderItems = orderItemService.getFromCurrentOrderByDishCategoryName(categoryName, userOrder.getId());
            if (orderItems.isEmpty()) {
                continue;
            }
            filteredOrderItems.addAll(orderItems);
        }

        request.setAttribute(ORDER_ITEM_LIST_JSP_ATTRIBUTE, filteredOrderItems);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, ORDER_ITEM_LIST_JSP_ATTRIBUTE));
        LOGGER.info("Filtered items will be shown");

        return ORDER_ITEM_LIST_JSP;
    }

}
