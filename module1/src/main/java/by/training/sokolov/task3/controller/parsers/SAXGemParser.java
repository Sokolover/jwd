package by.training.sokolov.task3.controller.parsers;

import by.training.sokolov.task3.model.Gem;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SAXGemParser {

    public List<Gem> parse(String path) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        SAXGemHandler handler = new SAXGemHandler();
        parser.parse(path, handler);

        return handler.getGems();
    }

}
