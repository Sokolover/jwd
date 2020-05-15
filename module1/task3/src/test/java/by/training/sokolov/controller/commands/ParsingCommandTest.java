package by.training.sokolov.controller.commands;

import by.training.sokolov.controller.GemAppController;
import by.training.sokolov.dal.GemDao;
import by.training.sokolov.service.SimpleGemService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class ParsingCommandTest {
    private static final Logger LOGGER = Logger.getLogger(GemAppController.class.getName());
    private static SimpleGemService gemService;


    @Before
    public void beforeStax() {
        GemDao gemDao = new GemDao();
        gemService = new SimpleGemService(gemDao);
        gemService.removeAll();
    }

    @Test
    public void domParsing() throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(this.getClass().getClassLoader().getResource("xml/gem.xml").getPath());
        Document document = builder.parse(file);
        gemService.inMemoryDom(document);

        Assert.assertEquals(gemService.findAll().size(), 2);
    }

    @Before
    public void beforeSax() {
        GemDao gemDao = new GemDao();
        gemService = new SimpleGemService(gemDao);
        gemService.removeAll();
    }

    @Test
    public void saxParsing() throws IOException, SAXException, ParserConfigurationException {

        gemService.inMemorySax(this.getClass().getClassLoader().getResource("xml/gem.xml").getPath());

        Assert.assertEquals(gemService.findAll().size(), 2);
    }

    @Before
    public void beforeDom() {
        GemDao gemDao = new GemDao();
        gemService = new SimpleGemService(gemDao);
        gemService.removeAll();
    }

    @Test
    public void staxParsing() throws XMLStreamException {

        gemService.inMemoryStax(this.getClass().getClassLoader().getResource("xml/gem.xml").getPath());

        Assert.assertEquals(gemService.findAll().size(), 2);
    }
}
