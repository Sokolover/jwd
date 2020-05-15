package by.training.sokolov.controller.commands;

import by.training.sokolov.controller.validators.XmlValidator;
import by.training.sokolov.service.GemService;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SaxParsingCommand extends GemParsingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(SaxParsingCommand.class.getName());
    private GemService service;

    SaxParsingCommand(GemService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("*** SAX parser works ***");

        String filePath = getFilePath(request);

        if (XmlValidator.checkXMLbyXSD(filePath)) {
            String msg = "XML is NOT corresponds to XSD";
            LOGGER.error(msg);
            return msg;
        }

        try {
            service.inMemorySax(filePath);
        } catch (ParserConfigurationException e) {
            String msg = "SAXException while parsing document in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (IOException e) {
            String msg = "IOException while parsing document in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (SAXException e) {
            String msg = "Exception while parsing document in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        }

        String msg = "got info from file";
        LOGGER.info(msg);

        return msg;
    }
}
