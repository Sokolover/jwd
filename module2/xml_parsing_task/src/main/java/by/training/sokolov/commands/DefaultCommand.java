package by.training.sokolov.commands;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DefaultCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        String message = "default command";
        LOGGER.info(message);
        return message;
    }
}
