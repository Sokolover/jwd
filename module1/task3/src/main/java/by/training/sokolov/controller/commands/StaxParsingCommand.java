package by.training.sokolov.controller.commands;

import by.training.sokolov.controller.validators.XmlValidator;
import by.training.sokolov.service.GemService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;

public class StaxParsingCommand extends GemParsingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(SaxParsingCommand.class.getName());
    private GemService service;

    StaxParsingCommand(GemService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("*** StAX parser works ***");

        String filePath = getFilePath(request);

        if (!XmlValidator.checkXMLbyXSD(filePath)) {
            String msg = "XML is NOT corresponds to XSD";
            LOGGER.error(msg);
            return msg;
        }

        try {
            service.inMemoryStax(filePath);
        } catch (XMLStreamException e) {
            LOGGER.error(e.getMessage());
        }

        String message = "got info from file";
        LOGGER.info(message);

        return service.findAll().toString();
    }
}
