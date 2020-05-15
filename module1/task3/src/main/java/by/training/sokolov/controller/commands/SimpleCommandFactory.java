package by.training.sokolov.controller.commands;

import by.training.sokolov.service.GemService;

import java.util.HashMap;
import java.util.Map;

public class SimpleCommandFactory implements CommandFactory {

    private Map<CommandEnum, Command> commandMap;

    public SimpleCommandFactory(GemService gemService) {
        commandMap = new HashMap<>();
        commandMap.put(CommandEnum.fromString("dom"), new DomParsingCommand(gemService));
        commandMap.put(CommandEnum.fromString("sax"), new SaxParsingCommand(gemService));
        commandMap.put(CommandEnum.fromString("stax"), new StaxParsingCommand(gemService));
        commandMap.put(CommandEnum.fromString("default"), new DefaultCommand());
    }

    @Override
    public Command getCommand(String commandName) {
        Command command = commandMap.get(CommandEnum.fromString(commandName));
        return command != null ? command : new DefaultCommand();
    }
}
