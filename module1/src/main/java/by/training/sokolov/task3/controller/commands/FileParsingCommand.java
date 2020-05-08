package by.training.sokolov.task3.controller.commands;

import by.training.sokolov.task3.controller.parsers.DOMGemParser;
import by.training.sokolov.task3.controller.parsers.SAXGemParser;
import by.training.sokolov.task3.controller.parsers.StAXGemParser;
import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.service.GemService;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static by.training.sokolov.task3.GemAppConstants.*;

public class FileParsingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(FileParsingCommand.class.getName());

    private GemService service;

    public FileParsingCommand() {

    }

    public FileParsingCommand(GemService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {

        String path = requestGetMap.get(QUERY_KEY_PATH);

        List<Gem> gems = null;

        switch (requestGetMap.get(PARSER_TYPE)) {
            case DOM:
                DOMGemParser domGemParser = new DOMGemParser();
                LOGGER.info("*** DOM parser works ***");
                try {
                    gems = domGemParser.parse(path);
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
            case SAX:
                SAXGemParser saxGemParser = new SAXGemParser();
                LOGGER.info("*** SAX parser works ***");
                try {
                    gems = saxGemParser.parse(path);
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
            case StAX:
                StAXGemParser staxGemParser = new StAXGemParser();
                LOGGER.info("*** StAX parser works ***");
                try {
                    gems = staxGemParser.parse(path);
                } catch (XMLStreamException e) {
                    LOGGER.error(e.getMessage());
                }
                break;
        }

        LOGGER.info("remove all records from table");
        service.removeAll();
        LOGGER.info("add new records to table");
        service.saveAll(gems);

        String message = "got info from file: " + path + ". ";
        LOGGER.info(message);

        return null;
    }


    @Override
    public String getName() {
        return QUERY_KEY_FILE_PARSE_COMMAND;
    }
}
