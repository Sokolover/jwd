package by.training.sokolov.task2.command;

import java.util.Map;

public class SimpleCommandFactory implements CommandFactory {

    private Map<String, Command> commandMap;

    public SimpleCommandFactory(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    @Override
    public Command getCommand(String commandName) {
        Command command = commandMap.get(commandName);
        if (command != null) {
            return command;
        } else {
            return new DefaultCommand();
        }
    }
}
