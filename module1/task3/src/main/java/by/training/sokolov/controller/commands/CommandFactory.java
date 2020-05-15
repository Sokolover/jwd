package by.training.sokolov.controller.commands;

public interface CommandFactory {

    Command getCommand(String commandName);
}
