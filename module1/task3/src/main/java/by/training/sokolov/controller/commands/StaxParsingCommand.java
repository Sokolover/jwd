package by.training.sokolov.task3.controller.commands;

import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.service.GemService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.util.List;

public class StaxParsingCommand implements Command{


    private final static Logger LOGGER = Logger.getLogger(SaxParsingCommand.class.getName());
    private GemService service;

    StaxParsingCommand(GemService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        List<Gem> gems = null;
        String path = "///"; //fixme получить путь из HttpServletRequest request
        LOGGER.info("*** StAX parser works ***");
        try {
            gems = service.inMemoryStax();
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("remove all records from table");
        service.removeAll();
        LOGGER.info("add new records to table");
        service.saveAll(gems);

        String message = "got info from file: " + path + ". ";
        LOGGER.info(message);

        return service.findAll().toString();
    }
}
