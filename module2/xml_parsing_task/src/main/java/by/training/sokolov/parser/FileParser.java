package by.training.sokolov.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface FileParser<T> {

    ParseResult<T> parse(String filePath) throws ParseException, ParserConfigurationException, SAXException, IOException, XMLStreamException;
}