package by.training.sokolov.task3.controller.commands;

import java.util.Map;

public interface Command {

    String execute(Map<String, String> requestGetMap);

    String getName();
}
