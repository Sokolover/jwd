package by.training.sokolov.parser;

import by.training.sokolov.model.Gem;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SaxFileParser implements FileParser<Gem> {

    private final static Logger LOGGER = Logger.getLogger(SaxFileParser.class.getName());

    @Override
    public ParseResult<Gem> parse(String filePath) throws ParseException, ParserConfigurationException, SAXException, IOException {

        LOGGER.info("*** SAX parser works ***");

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        SaxGemHandler handler = new SaxGemHandler();
        parser.parse(filePath, handler);

        List<Gem> gems = handler.getGems();
        String message = "successful Sax parsing";
        ParseResult<Gem> result = new ParseResult<>(gems, message);
        return result;

    }
}
