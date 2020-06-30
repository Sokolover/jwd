package by.training.sokolov.command;

import by.training.sokolov.core.security.SecurityContext;
import by.training.sokolov.command.constants.CommandType;
import by.training.sokolov.command.order.CreateOrderCommand;
import by.training.sokolov.command.order.DeleteDishFromOrderCommand;
import by.training.sokolov.command.order.DisplayOrderDishListCommand;
import by.training.sokolov.command.order.OrderItemAddCommand;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFactoryImpl implements CommandFactory {

    private static final Map<CommandType, Command> commands = new ConcurrentHashMap<>(CommandType.values().length);

    public CommandFactoryImpl() {

        commands.put(CommandType.ORDER_BASKET_SERVLET_SWITCH, (request, response) -> "order_basket");
        commands.put(CommandType.MENU_SERVLET_SWITCH, (request, response) -> "menu");
        commands.put(CommandType.REGISTER_SERVLET_SWITCH, (request, response) -> "user_register");
        commands.put(CommandType.LOGIN_SERVLET_SWITCH, (request, response) -> "login");

        commands.put(CommandType.DELETE_DISH_FROM_ORDER, new DeleteDishFromOrderCommand());
        commands.put(CommandType.CREATE_ORDER, new CreateOrderCommand());
        commands.put(CommandType.ORDER_DISH_LIST_DISPLAY, new DisplayOrderDishListCommand());
        commands.put(CommandType.ORDER_ITEM_ADD, new OrderItemAddCommand());

        commands.put(CommandType.VIEW_DISH_MENU, new ViewDishMenuCommand());

        commands.put(CommandType.REGISTER_USER, new RegisterUserCommand());
        commands.put(CommandType.LOGIN_SUBMIT, new LoginSubmitCommand());
        commands.put(CommandType.LOGOUT, (request, response) -> {
            request.getSession().invalidate();
            SecurityContext.getInstance().logout(request.getSession().getId());
            return "logout";
        });

        commands.put(CommandType.VIEW_USER_LIST, new ViewUserListCommand());
        commands.put(CommandType.INDEX, (request, response) -> "index");
        /*
         почему-то именно с названием команды DISH_MENU_DISPLAY посылается
         2 команды: INDEX и DISH_MENU_DISPLAY.
         при этом DISH_MENU_DISPLAY посылается со статусом 302 и не приходит на сервлет,
         а приходит INDEX. поэтому название команды я поменял на
         VIEW_DISH_MENU (перейти на сервлет меню) и DISH_MENU_SUBMIT(вывести меню)
         */
//        commands.put(CommandType.DISH_MENU_DISPLAY, new ViewMenuCommand());
    }

    public Command getCommand(String commandParam) {

        return CommandType.of(commandParam).map(commands::get).orElse(((request, response) -> "index"));
    }
}
