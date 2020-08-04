package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;
import by.training.sokolov.util.CategoryNameUtil;
import by.training.sokolov.util.JspUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.ORDER_ITEM_LIST_JSP_ATTRIBUTE;
import static by.training.sokolov.core.constants.JspName.ORDER_ITEM_LIST_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static java.lang.String.format;

public class DisplayOrderItemListCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DisplayOrderItemListCommand.class.getName());

    private final UserOrderService userOrderService;
    private final OrderItemService orderItemService;
    private final DishCategoryService dishCategoryService;

    public DisplayOrderItemListCommand(UserOrderService userOrderService, OrderItemService orderItemService, DishCategoryService dishCategoryService) {
        this.userOrderService = userOrderService;
        this.orderItemService = orderItemService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder currentOrder = userOrderService.getBuildingUpUserOrder(sessionId);

        JspUtil jspUtil = ApplicationContext.getInstance().getBean(JspUtil.class);
        jspUtil.setCategoriesAttribute(request);

        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(currentOrder.getId());
            request.setAttribute(ORDER_ITEM_LIST_JSP_ATTRIBUTE, orderItems);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, ORDER_ITEM_LIST_JSP_ATTRIBUTE, orderItems.toString()));
            LOGGER.info("All found items will be shown");

            return ORDER_ITEM_LIST_JSP;
        }

        List<OrderItem> filteredOrderItems = new ArrayList<>();
        for (String categoryName : categoryNames) {
            List<OrderItem> orderItems = orderItemService.getFromCurrentOrderByDishCategoryName(categoryName, currentOrder.getId());
            if (orderItems.isEmpty()) {
                continue;
            }
            filteredOrderItems.addAll(orderItems);
        }

        request.setAttribute(ORDER_ITEM_LIST_JSP_ATTRIBUTE, filteredOrderItems);
        LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, ORDER_ITEM_LIST_JSP_ATTRIBUTE, filteredOrderItems.toString()));
        LOGGER.info("Filtered items will be shown");

        return ORDER_ITEM_LIST_JSP;
    }

}
