package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import org.apache.log4j.Logger;

import java.util.Map;

public class DefaultCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());
    private final String name = LibraryAppConstants.DEFAULT_COMMAND;

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("default command");
        return "";
    }

    @Override
    public String getName() {
        return name;
    }
}
