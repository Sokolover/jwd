package by.training.sokolov.task2.command;

public interface CommandFactory {

    Command getCommand(String commandName);
}
