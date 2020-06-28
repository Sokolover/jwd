package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFactoryImpl implements CommandFactory {

    private static final Map<CommandType, Command> commands = new ConcurrentHashMap<>(CommandType.values().length);

    public CommandFactoryImpl() {

        commands.put(CommandType.CREATE_ORDER, new CreateOrderCommand());

        commands.put(CommandType.BASKET_ITEM_ADD, new BasketItemAddCommand());
        commands.put(CommandType.BASKET_DISPLAY, (request, response) -> "basket");
        commands.put(CommandType.DISH_MENU_DISPLAY, (request, response) -> "menu");
        commands.put(CommandType.DISH_MENU_SUBMIT, new ViewMenuCommand());
//        commands.put(CommandType.DISH_MENU_DISPLAY, new ViewMenuCommand());
        commands.put(CommandType.REGISTER_DISPLAY, (request, response) -> "user_register");
        commands.put(CommandType.REGISTER_SUBMIT, new RegisterUserCommand());
        commands.put(CommandType.LOGIN_DISPLAY, (request, response) -> "login");
        commands.put(CommandType.LOGIN_SUBMIT, new LoginSaveCommand());
//        commands.put(CommandType.LOGOUT, (request, response) -> {
//            request.getSession().invalidate();
//            return "redirect:/?_command=" + CommandType.INDEX;
//        });
        commands.put(CommandType.LOGOUT, (request, response) -> {
            request.getSession().invalidate();
            SecurityContext.getInstance().logout(request.getSession().getId());
            return "logout";
        });
        commands.put(CommandType.VIEW_USER_LIST, new ViewUserListCommand());
        commands.put(CommandType.INDEX, (request, response) -> "index");
    }

    public Command getCommand(String commandParam) {

        return CommandType.of(commandParam).map(commands::get).orElse(((request, response) -> "index"));
    }

}
