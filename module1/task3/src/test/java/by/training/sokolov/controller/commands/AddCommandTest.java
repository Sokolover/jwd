package by.training.sokolov.controller.commands;

import by.training.sokolov.controller.GemAppController;
import by.training.sokolov.dal.GemDao;
import by.training.sokolov.service.SaxGemHandler;
import by.training.sokolov.service.SimpleGemService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class AddCommandTest {
    private static final Logger LOGGER = Logger.getLogger(GemAppController.class.getName());
    private static SimpleGemService gemService;
    private static SimpleCommandFactory commandFactory;


    @Before
    public void beforeDom() {
        GemDao gemDao = new GemDao();
        gemService = new SimpleGemService(gemDao);
        commandFactory = new SimpleCommandFactory(gemService);
        gemService.removeAll();
        LOGGER.info("init test server");
    }

    @Test
    public void domParsing() throws IOException, SAXException, ParserConfigurationException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(this.getClass().getClassLoader().getResource("gem.xml").getPath());
        Document document = builder.parse(file);

        gemService.inMemoryDom(document);

    }

    @Test
    public void saxParsing() throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SaxGemHandler handler = new SaxGemHandler();
        File file = new File(this.getClass().getClassLoader().getResource("gem.xml").getPath());
        parser.parse(file, handler);
        Assert.assertEquals(gemService.findAll().size(), 2);
    }

    @Test
    void staxParsing() throws IOException, SAXException, ParserConfigurationException {

        File file = new File(this.getClass().getClassLoader().getResource("gem.xml").getPath());
        InputStream targetStream = new FileInputStream(file);
//        gemService.inMemoryStax(targetStream);

        Assert.assertEquals(gemService.findAll().size(), 2);
    }
}