package by.training.sokolov.command;

import java.util.Map;

public interface Command {

    String execute(Map<String, String> requestGetMap);

    String getName();
}
