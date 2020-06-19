package by.training.sokolov.command;

import by.training.sokolov.model.User;
import by.training.sokolov.service.GenericService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFactoryImpl implements CommandFactory {

    private static final Map<CommandType, Command> commands = new ConcurrentHashMap<>(CommandType.values().length);

    public CommandFactoryImpl(GenericService<User> userService) {
//        commands.put(CommandType.VIEW_USER_DETAILS, new ViewUserDetail());
//        commands.put(CommandType.EDIT_VIEW_USER, new EditUserViewCommand());
//        commands.put(CommandType.EDIT_SAVE_USER, new EditUserSaveCommand());
//        commands.put(CommandType.DELETE_USER, new DeleteUserCommand());
        commands.put(CommandType.VIEW_USER_LIST, new ViewUserListCommand());
        commands.put(CommandType.CREATE_USER, (request, response) -> "create");
        commands.put(CommandType.REGISTER, new RegisterUserCommand(userService));
        commands.put(CommandType.LOGIN_DISPLAY, (request, response) -> "login");
        commands.put(CommandType.LOGIN_SUBMIT, new LoginSaveCommand(userService));
        commands.put(CommandType.LOGOUT, (request, response) -> {

            request.getSession().invalidate();
            return "redirect:/?_command=" + CommandType.INDEX;
        });
        commands.put(CommandType.INDEX, (request, response) -> "index");
    }

    public Command getCommand(String commandParam) {

        return CommandType.of(commandParam).map(commands::get).orElse(((request, response) -> "index"));
    }

}
