package by.training.sokolov.command.order;

import by.training.sokolov.command.CategoryNameUtil;
import by.training.sokolov.command.Command;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.category.model.DishCategory;
import by.training.sokolov.entity.category.service.DishCategoryService;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.orderitem.model.OrderItem;
import by.training.sokolov.entity.orderitem.service.OrderItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static by.training.sokolov.application.constants.JspName.*;

public class OrderDishListDisplayCommand implements Command {

    private final UserOrderService userOrderService;
    private final OrderItemService orderItemService;
    private final DishCategoryService dishCategoryService;

    public OrderDishListDisplayCommand(UserOrderService userOrderService, OrderItemService orderItemService, DishCategoryService dishCategoryService) {
        this.userOrderService = userOrderService;
        this.orderItemService = orderItemService;
        this.dishCategoryService = dishCategoryService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder userOrder = userOrderService.getCurrentUserOrder(sessionId);

        if (Objects.isNull(userOrder)) {

            request.setAttribute("error", "please, create order");
            return ERROR_MESSAGE_JSP;
        }

        setCategoriesToRequest(request);
        List<String> categoryNames = CategoryNameUtil.getCategoryNamesFromRequest(request);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals(CategoryNameUtil.ALL_CATEGORIES)) {

            List<OrderItem> orderItems = orderItemService.findAllItemsByOrderId(userOrder.getId());
            request.setAttribute("orderItems", orderItems);
            return ORDER_ITEM_LIST_JSP;
        }

        List<OrderItem> filteredUserOrderItems = new ArrayList<>();
        for (String categoryName : categoryNames) {
            OrderItem orderItem = orderItemService.getFromCurrentOrderByDishCategoryName(categoryName, userOrder.getId());
            if (Objects.isNull(orderItem)) {
                continue;
            }
            filteredUserOrderItems.add(orderItem);
        }

        request.setAttribute("orderItems", filteredUserOrderItems);

        return ORDER_ITEM_LIST_JSP;
    }

    private void setCategoriesToRequest(HttpServletRequest request) throws SQLException, ConnectionException {

        request.setAttribute("category", DISH_CATEGORY_JSP);
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);
    }
}
