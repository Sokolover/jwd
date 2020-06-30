package by.training.sokolov.command.order;

import by.training.sokolov.core.security.SecurityContext;
import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.category.service.DishCategoryService;
import by.training.sokolov.command.Command;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.dish.model.Dish;
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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static by.training.sokolov.command.constants.CommandReturnValues.ORDER_DISH_LIST_DISPLAY;

public class DisplayOrderDishListCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        /*
        todo сделать запрос в дао, который достаёт items из order_item таблицы, которые соотв.
            текущему order'y!!!
         */

        /*
        todo DisplayOrderDishListCommand и ViewDishMenuCommand должны иметь фильтрацию
            по категориям и их необходимо унифицировать в плане фильтра блюд!
            фильтр по категориям необходимо оптимизировать
         */

        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> paramList = new ArrayList<>();
        List<String[]> paramValues = new ArrayList<>();

        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();
            paramValues.add(request.getParameterValues(paramName));
//            paramList.add(paramName);
        }

        List<String> reqValues = new ArrayList<>();
        for (String[] paramValue : paramValues) {
            reqValues.addAll(Arrays.asList(paramValue));
        }

        UserOrder currentUserOrder = getCurrentUserOrder(request);
        OrderItemService orderItemService = BeanFactory.getOrderItemService();
        List<OrderItem> orderItems = orderItemService.findAll();
        List<OrderItem> userOrderItems = new ArrayList<>();
        List<OrderItem> filteredUserOrderItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getUserOrder().getId().equals(currentUserOrder.getId())) {
                userOrderItems.add(orderItem);
            }
        }

        for (String categoryName : reqValues) {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getDish().getDishCategory().getCategoryName().equals(categoryName)) {
                    filteredUserOrderItems.add(orderItem);
                }
            }
        }

        if (filteredUserOrderItems.isEmpty()) {
            request.setAttribute("orderItems", userOrderItems);
        } else {
            request.setAttribute("orderItems", filteredUserOrderItems);
        }

        DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);

        return ORDER_DISH_LIST_DISPLAY;
    }

    private UserOrder getCurrentUserOrder(HttpServletRequest request) throws SQLException {
        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        return userOrderService.findInProgressUserOrder(currentUser.getId());
    }
}
