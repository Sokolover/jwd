package by.training.sokolov.command;

import by.training.sokolov.command.constants.CommandType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static by.training.sokolov.command.constants.CommandReturnValues.DEFAULT_RESULT;

public class CommandFactoryImpl implements CommandFactory {

    private static final Map<CommandType, Command> commands = new ConcurrentHashMap<>(CommandType.values().length);

    public CommandFactoryImpl() {

    }

    @Override
    public void registerCommand(CommandType commandName, Command command) {

        commands.put(commandName, command);
    }

    @Override
    public Command getCommand(String commandParam) {

        return CommandType.of(commandParam)
                .map(commands::get)
                .orElse(((request, response) -> DEFAULT_RESULT));
    }
}
