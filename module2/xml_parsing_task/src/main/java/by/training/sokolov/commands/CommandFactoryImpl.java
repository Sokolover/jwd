package by.training.sokolov.commands;

import by.training.sokolov.service.GemService;

import java.util.HashMap;
import java.util.Map;

public class CommandFactoryImpl implements CommandFactory {

    private Map<CommandEnum, Command> commandMap;

    public CommandFactoryImpl(GemService gemService) {
        commandMap = new HashMap<>();
        commandMap.put(CommandEnum.fromString("upload"), new UploadGemsCommand(gemService));
        commandMap.put(CommandEnum.fromString("download"), new DownloadCommand(gemService));
        commandMap.put(CommandEnum.fromString("delete_all"), new DeleteAllCommand(gemService));
        commandMap.put(CommandEnum.fromString("default"), new DefaultCommand());
    }

    @Override
    public Command getCommand(String commandName) {
        Command command = commandMap.get(CommandEnum.fromString(commandName));
        return command != null ? command : new DefaultCommand();
    }
}
