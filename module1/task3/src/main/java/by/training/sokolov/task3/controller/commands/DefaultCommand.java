package by.training.sokolov.task3.controller.commands;

import org.apache.log4j.Logger;

import java.util.Map;

import static by.training.sokolov.task3.GemAppConstants.DEFAULT_COMMAND;

public class DefaultCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("default command");
        return "";
    }

    @Override
    public String getName() {
        return DEFAULT_COMMAND;
    }
}
