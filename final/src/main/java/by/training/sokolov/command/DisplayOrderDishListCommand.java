package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.category.service.DishCategoryService;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.order.model.UserOrder;
import by.training.sokolov.order.service.UserOrderService;
import by.training.sokolov.orderitem.model.OrderItem;
import by.training.sokolov.orderitem.service.OrderItemService;
import by.training.sokolov.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisplayOrderDishListCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        /*
        todo сделать запрос в дао, который достаёт items из order_item таблицы, которые соотв.
            текущему order'y!!!
         */

        UserOrder currentUserOrder = getCurrentUserOrder(request);
        OrderItemService orderItemService = BeanFactory.getOrderItemService();
        List<OrderItem> orderItems = orderItemService.findAll();
        List<OrderItem> userOrderItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getUserOrder().getId().equals(currentUserOrder.getId())) {
                userOrderItems.add(orderItem);
            }
        }

        request.setAttribute("orderItems", userOrderItems);

        DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);

        return "order_dish_list_display";
    }

    private UserOrder getCurrentUserOrder(HttpServletRequest request) throws SQLException {
        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        return userOrderService.findInProgressUserOrder(currentUser.getId());
    }
}
