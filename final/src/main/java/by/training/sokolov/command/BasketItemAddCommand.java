package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.dish.model.Dish;
import by.training.sokolov.dish.service.DishService;
import by.training.sokolov.order.model.UserOrder;
import by.training.sokolov.order.service.UserOrderService;
import by.training.sokolov.orderitem.model.OrderItem;
import by.training.sokolov.user.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

public class BasketItemAddCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String dishIdString = request.getParameter("dish.id");
        Long dishIdLong = Long.parseLong(dishIdString);
        DishService dishService = BeanFactory.getDishService();
        dishService.getById(dishIdLong);

        String dishAmountString = request.getParameter("order.menu.amount");
        Integer dishAmountInteger = Integer.parseInt(dishAmountString);

        OrderItem orderItem = new OrderItem();

        orderItem.getDish().setId(dishIdLong);

        orderItem.setDishAmount(dishAmountInteger);

        Dish dish = dishService.getById(dishIdLong);
        BigDecimal itemCost = dish.getCost().multiply(BigDecimal.valueOf(dishAmountInteger));
        orderItem.setItemCost(itemCost);

        String currentSessionId = request.getSession().getId();
        User currentUser = SecurityContext.getInstance().getCurrentUser(currentSessionId);
        UserOrderService userOrderService = BeanFactory.getUserOrderService();
        UserOrder currentUserOrder = userOrderService.findInProgressUserOrder(currentUser.getId());
        Long currentUserOrderId = currentUserOrder.getId();

        orderItem.getUserOrder().setId(currentUserOrderId);

        /*
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

        todo part2
            8.1 сделать проверку можно ли добавлять в список блюда, елси они уже есть в списке
            8.2 сделать просмотр блюд заказа и возможность их удаления из заказа
            9. сделать вкладку оформления заказа где
                9.1 обязательно будет таблица с перечислением блюд, форма куда и как доставлять
                9.2 будет всё остальное с прототипа UI
         */

//        private Long id;
//        private Integer dishAmount;
//        private Integer itemCost;
//        private Dish dish;
//        private UserOrder userOrder;

        return "basket_add";
    }
}
