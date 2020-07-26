package by.training.sokolov.command;

import by.training.sokolov.command.constants.CommandType;

public interface CommandFactory {

    Command getCommand(String commandName);

    void registerCommand(CommandType commandName, Command command);
}