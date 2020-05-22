package by.training.sokolov.commands;

import by.training.sokolov.service.GemService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAllCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DeleteAllCommand.class.getName());

    private GemService gemService;

    DeleteAllCommand(GemService gemService) {
        this.gemService = gemService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        gemService.removeAll();

        String message = "all records have been removed";
        LOGGER.info(message);
        return message;
    }
}
