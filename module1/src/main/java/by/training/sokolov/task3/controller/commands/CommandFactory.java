package by.training.sokolov.task3.controller.commands;

public interface CommandFactory {

    Command getCommand(String commandName);
}
