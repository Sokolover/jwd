package by.training.sokolov.command;

import by.training.sokolov.command.constants.CommandType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandFactoryImpl implements CommandFactory {

    private static final Map<CommandType, Command> commands = new ConcurrentHashMap<>(CommandType.values().length);

    public CommandFactoryImpl() {

    }
        /*
        ЭТО ИЗ-ЗА SecurityContext и security.property

         почему-то именно с названием команды DISH_MENU_DISPLAY посылается
         2 команды: INDEX и DISH_MENU_DISPLAY.
         при этом DISH_MENU_DISPLAY посылается со статусом 302 и не приходит на сервлет,
         а приходит INDEX. поэтому название команды я поменял на
         VIEW_DISH_MENU (перейти на сервлет меню) и DISH_MENU_SUBMIT(вывести меню)
         */

    @Override
    public void registerCommand(CommandType commandName, Command command) {

        commands.put(commandName, command);
    }

    /*
    todo сделать getCommand как в прошлых проектах, по-простому
     */

    @Override
    public Command getCommand(String commandParam) {

        return CommandType.of(commandParam)
                .map(commands::get)
                .orElse(((request, response) -> "index"));
    }
}
