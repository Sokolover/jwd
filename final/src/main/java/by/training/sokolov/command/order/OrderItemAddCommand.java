package by.training.sokolov.command.order;

import by.training.sokolov.core.security.SecurityContext;
import by.training.sokolov.command.Command;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.dish.service.DishService;
import by.training.sokolov.order.model.UserOrder;
import by.training.sokolov.order.service.UserOrderService;
import by.training.sokolov.orderitem.model.OrderItem;
import by.training.sokolov.orderitem.service.OrderItemService;
import by.training.sokolov.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.command.constants.CommandReturnValues.BASKET_ADD_ITEM;

public class OrderItemAddCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        Long currentUserOrderId = getCurrentUserOrderId(request);

        List<OrderItem> orderItems = BeanFactory.getOrderItemService().findAll();

        String dishIdString = request.getParameter("dish.id");
        Long dishIdLong = Long.parseLong(dishIdString);

        /*
        todo в дальнейшем заменить каким-то запросом в OrderItemDao, если буду оставлять логику
            "нельзя добавлять блюдо в заказ если оно уже есть в заказе, можно только удалить
            весь OrderItem и добавить новый"
            @
            этот цикл  не работает так как надо и описано выше
            @
            надо сделать запрос в бд
         */

        if (orderItems.isEmpty()) {
            addNewOrderItem(request, currentUserOrderId);
        } else {
            for (OrderItem orderItem : orderItems) {
                if (orderItem.getUserOrder().getId().equals(currentUserOrderId)
                        && !orderItem.getDish().getId().equals(dishIdLong)) {
                    addNewOrderItem(request, currentUserOrderId);
                }
            }
        }

        /*
        todo part2
            8.1 сделать проверку можно ли добавлять в список блюда, если они уже есть в списке !
            8.2 сделать просмотр item'ов заказа и возможность их удаления из заказа +
            8.3 может быть надо сделать возможность удаления заказа

         todo part3
            9. сделать вкладку оформления заказа где
                9.1 обязательно будет таблица с перечислением блюд, форма куда и как доставлять
                9.2 будет всё остальное с прототипа UI
         */

        return BASKET_ADD_ITEM;
    }

    /*
            todo проверить всё ли сделано по part1

        todo part1 где будет UserOrder, как ему присваевать OrderItem
            0. добавить поле активности заказа в таблицу +
            1. захожу в личный кабинет
            2. нажимаю кнопку "создать заказ"
            в этот момент у меня создаётся новый UserOrder +
            (с меткой активный по дефолту) и кладётся в таблицу +
            3. нажимаю кнопку Add в меню блюд, id блюда летит в OrderBasketServlet !
            4. из request по текущей сессии достаём юзера +
            5. из юзера достаём текущий заказ +
             для этого:
              5.1 достаём активный заказ из базы
              5.2 активен может быть только один заказ, остальные считаются завершёнными
              5.3 когда заказ будет выполнен, ему нужно присвоеить статус неактивного
            6. создаём OrderItem по принятым параметрам с request'a +
            7. кладём id текущего заказа юзера в созданную OrderItem +
     */

    private void addNewOrderItem(HttpServletRequest request, Long currentUserOrderId) throws SQLException {

        OrderItem orderItem = new OrderItem();

        String dishIdString = request.getParameter("dish.id");
        Long dishIdLong = Long.parseLong(dishIdString);
        DishService dishService = BeanFactory.getDishService();
        Dish dish = dishService.getById(dishIdLong);
        orderItem.getDish().setId(dishIdLong);

        String dishAmountString = request.getParameter("order.dish.amount");
        Integer dishAmountInteger = Integer.parseInt(dishAmountString);
        orderItem.setDishAmount(dishAmountInteger);

        BigDecimal itemCost = dish.getCost().multiply(BigDecimal.valueOf(dishAmountInteger));
        orderItem.setItemCost(itemCost);

        orderItem.getUserOrder().setId(currentUserOrderId);

        OrderItemService orderItemService = BeanFactory.getOrderItemService();
        orderItemService.save(orderItem);
    }

    private Long getCurrentUserOrderId(HttpServletRequest request) throws SQLException {
        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        UserOrder currentUserOrder = userOrderService.findInProgressUserOrder(currentUser.getId());
        return currentUserOrder.getId();
    }
}
