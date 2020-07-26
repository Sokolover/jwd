package by.training.sokolov.commands;

import by.training.sokolov.dal.GemDao;
import by.training.sokolov.dal.GemDaoImpl;
import by.training.sokolov.model.Gem;
import by.training.sokolov.parser.FileParser;
import by.training.sokolov.parser.ParseException;
import by.training.sokolov.parser.ParseResult;
import by.training.sokolov.parser.StaxFileParser;
import by.training.sokolov.service.GemService;
import by.training.sokolov.service.SimpleGemService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class ParsingCommandTest {

    private static GemService gemService;
    List<Gem> gems;
    FileParser<Gem> parser;

    @Before
    public void beforeParser() {
        GemDao gemDao = new GemDaoImpl();
        gemService = new SimpleGemService(gemDao);
        gemService.removeAll();
    }

    @Test
    public void domParsing() throws ParserConfigurationException, XMLStreamException, SAXException, ParseException, IOException {

        parser = new StaxFileParser();
        ParseResult<Gem> parseResult = parser.parse(this.getClass().getClassLoader().getResource("xml/gem.xml").getPath());
        gems = parseResult.getParseResults();
        gemService.saveAll(gems);
        Assert.assertEquals(gemService.findAll().size(), 2);
    }

    @Test
    public void saxParsing() throws ParserConfigurationException, XMLStreamException, SAXException, ParseException, IOException {

        parser = new StaxFileParser();
        ParseResult<Gem> parseResult = parser.parse(this.getClass().getClassLoader().getResource("xml/gem.xml").getPath());
        gems = parseResult.getParseResults();
        gemService.saveAll(gems);
        Assert.assertEquals(gemService.findAll().size(), 2);
    }

    @Test
    public void staxParsing() throws ParserConfigurationException, XMLStreamException, SAXException, ParseException, IOException {

        parser = new StaxFileParser();
        ParseResult<Gem> parseResult = parser.parse(this.getClass().getClassLoader().getResource("xml/gem.xml").getPath());
        gems = parseResult.getParseResults();
        gemService.saveAll(gems);
        Assert.assertEquals(gemService.findAll().size(), 2);
    }

}
