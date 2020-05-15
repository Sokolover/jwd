package by.asite.secondeditionserver.commands;

import by.asite.secondeditionserver.AppConstants;
import by.asite.secondeditionserver.dao.TariffsData;
import by.asite.secondeditionserver.dao.TariffsDataImpl;
import by.asite.secondeditionserver.services.SAXHandler;
import by.asite.secondeditionserver.services.TariffsService;
import by.asite.secondeditionserver.services.TariffsServiceImpl;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class AddCommandTest {
    static final Logger LOGGER = AppConstants.COMMANDS_LOGGER;
    static TariffsService tariffsService;
    static CommandsFactory commandsFactory;

    @BeforeAll
    static void beforeAll() {
        TariffsData tariffsData = new TariffsDataImpl();
        tariffsService = new TariffsServiceImpl(tariffsData);
        commandsFactory = new CommandsFactoryImpl(tariffsService);
        LOGGER.info("init test server");
    }

    @BeforeEach
    void clean() {
        tariffsService.deleteAllData();
    }

    @Test
    void addDOM() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File file = new File(this.getClass().getClassLoader().getResource("input.xml").getPath());
        Document document = builder.parse(file);
        try {
            tariffsService.domToData(document);
            Assert.assertEquals(tariffsService.getTariffsData().getTariffsListFromDAL().size(), 3);
        } catch (Exception e) {
            LOGGER.error("test add dom error", e);
        }
    }

    @Test
    void addSAX() throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler(tariffsService);
        File file = new File(this.getClass().getClassLoader().getResource("input.xml").getPath());
        parser.parse(file, handler);
        Assert.assertEquals(tariffsService.getTariffsData().getTariffsListFromDAL().size(), 3);
    }

    @Test
    void addSTAX() throws IOException, SAXException, ParserConfigurationException {

        File file = new File(this.getClass().getClassLoader().getResource("input.xml").getPath());
        InputStream targetStream = new FileInputStream(file);

        String x = tariffsService.doaddstax(targetStream);

        Assert.assertEquals(tariffsService.getTariffsData().getTariffsListFromDAL().size(), 3);
    }
}