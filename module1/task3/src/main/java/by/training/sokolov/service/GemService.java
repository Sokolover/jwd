package by.training.sokolov.service;

import by.training.sokolov.model.Gem;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface GemService {

    void saveAll(List<Gem> publicationList);

    List<Gem> findAll();

    void removeAll();

    void inMemoryDom(Document document) throws ParserConfigurationException, IOException, SAXException;

    void inMemorySax(String filePath) throws ParserConfigurationException, SAXException, IOException;

    void inMemoryStax(String filePath) throws XMLStreamException;
}
