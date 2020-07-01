package by.training.sokolov.command.order;

import by.training.sokolov.category.model.DishCategory;
import by.training.sokolov.category.service.DishCategoryService;
import by.training.sokolov.command.CategoryNameUtil;
import by.training.sokolov.command.Command;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.core.security.SecurityContext;
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

import static by.training.sokolov.ApplicationModule.DISH_MENU;
import static by.training.sokolov.command.constants.CommandReturnValues.ORDER_DISH_LIST_DISPLAY;

public class OrderDishListDisplayCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        /*
        todo ВРОДЕ СДЕЛАНО DisplayOrderDishListCommand и ViewDishMenuCommand должны иметь фильтрацию
            по категориям и их необходимо унифицировать в плане фильтра блюд!
            фильтр по категориям необходимо оптимизировать
         */

        OrderItemService orderItemService = BeanFactory.getOrderItemService();
        UserOrder userOrder = getCurrentUserOrder(request);
        List<OrderItem> userOrderItems = orderItemService.findAllItemsByOrderId(userOrder.getId());

        setCategoriesToRequest(request);

        List<String> categoryNames = CategoryNameUtil.getCategoryNames(request);

        if (categoryNames.isEmpty() || categoryNames.get(0).equals("all")) {

            request.setAttribute("orderItems", userOrderItems);
            return DISH_MENU;
        }

        List<OrderItem> filteredUserOrderItems = new ArrayList<>();

        List<OrderItem> orderItems = orderItemService.findAll();

        for (String categoryName : categoryNames) {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getDish().getDishCategory().getCategoryName().equals(categoryName)) {
                    filteredUserOrderItems.add(orderItem);
                }
            }
        }

        request.setAttribute("orderItems", filteredUserOrderItems);

        return ORDER_DISH_LIST_DISPLAY;
    }

    private void setCategoriesToRequest(HttpServletRequest request) throws SQLException {

        DishCategoryService dishCategoryService = BeanFactory.getDishCategoryService();
        List<DishCategory> categories = dishCategoryService.findAll();
        request.setAttribute("categoryList", categories);
    }

    private UserOrder getCurrentUserOrder(HttpServletRequest request) throws SQLException {

        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        return userOrderService.findInProgressUserOrder(currentUser.getId());
    }
}
