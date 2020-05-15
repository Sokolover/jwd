package by.training.sokolov.task3.service;

import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.dal.GemDao;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface GemService {

    List<Gem> createGemListFromFile(String[] publicationParams);

    Gem buildGemFromFile(String info);

    GemDao getGemDao();

    void saveAll(List<Gem> publicationList);

    List<Gem> findAll();

    void removeAll();

    List<Gem> inMemoryDom(Document document) throws ParserConfigurationException, IOException, SAXException;

    List<Gem> inMemorySax() throws ParserConfigurationException, SAXException, IOException;

    List<Gem> inMemoryStax() throws XMLStreamException;
}
