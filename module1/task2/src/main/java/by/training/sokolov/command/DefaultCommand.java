package by.training.sokolov.command;

import by.training.sokolov.LibraryAppConstants;
import org.apache.log4j.Logger;

import java.util.Map;

public class DefaultCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("default command");
        return "";
    }

    @Override
    public String getName() {
        return LibraryAppConstants.DEFAULT_COMMAND;
    }
}
