package by.training.sokolov.controller.commands;

import by.training.sokolov.controller.validators.XmlValidator;
import by.training.sokolov.service.GemService;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DomParsingCommand extends GemParsingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(DomParsingCommand.class.getName());
    private GemService service;

    DomParsingCommand(GemService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("*** DOM parser works ***");

        String filePath = getFilePath(request);

        if (XmlValidator.checkXMLbyXSD(filePath)) {
            String msg = "XML is NOT corresponds to XSD";
            LOGGER.error(msg);
            return msg;
        }

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            String msg = "cannot find file";
            LOGGER.error(msg, e);
            return msg;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            String msg = "newDocumentBuilder() exception in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        }

        try {
            document = builder.parse(inputStream);
        } catch (SAXException e) {
            String msg = "SAXException while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (IOException e) {
            String msg = "IOException while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        } catch (Exception e) {
            String msg = "Exception while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return msg;
        }

        try {
            service.inMemoryDom(document);
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
