package by.training.sokolov.commands;

public interface CommandFactory {

    Command getCommand(String commandName);
}
