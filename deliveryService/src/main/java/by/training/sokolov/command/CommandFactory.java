package by.training.sokolov.command;

public interface CommandFactory {

    Command getCommand(String commandName);

    void registerCommand(CommandType commandName, Command command);
}