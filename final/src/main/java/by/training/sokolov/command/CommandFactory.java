package by.training.sokolov.command;

public interface CommandFactory {

    Command getCommand(String commandName);
}