package by.training.sokolov.task3.service;

import by.training.sokolov.task3.dal.GemDao;
import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.model.GemEnum;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.task3.model.GemEnum.GEM;
import static by.training.sokolov.task3.model.GemEnum.GEMS;

public class SimpleGemService implements GemService {

    private final static Logger LOGGER = Logger.getLogger(SimpleGemService.class.getName());
    private GemDao gemDao;

    public SimpleGemService(GemDao gemDao) {
        this.gemDao = gemDao;
    }

    @Override
    public List<Gem> createGemListFromFile(String[] publicationParams) {
        return null;
    }

    @Override
    public Gem buildGemFromFile(String info) {
        return null;
    }

    @Override
    public GemDao getGemDao() {
        return null;
    }

    @Override
    public void saveAll(List<Gem> gems) {
        gemDao.saveAll(gems);
    }

    @Override
    public List<Gem> findAll() {
        LOGGER.info("findAll()");
        return gemDao.getGemList();
    }

    @Override
    public void removeAll() {
        LOGGER.info("removeAll()");
        gemDao.setGemList(new ArrayList<>());
    }

    @Override
    public List<Gem> inMemoryStax() throws XMLStreamException {

        List<Gem> gemList = null;
        Gem currentGem = null;
        String tagContent = null;

        //todo сделать чтобы читалось из String path а не из "xml/gem.xml" !!!
        XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(new File("ПЕРЕДАТЬ ПУТЬ ИЛИ ФЙЛ С РЕКУЭСТ"));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }

        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (GemEnum.fromString(reader.getLocalName()) == GEM) {
                        currentGem = new Gem();
                        currentGem.setId(reader.getAttributeValue(0));
                    }
                    if (GemEnum.fromString(reader.getLocalName()) == GEMS) {
                        gemList = new ArrayList<>();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (GemEnum.fromString(reader.getLocalName())) {
                        case GEM:
                            gemList.add(currentGem);
                            break;
                        case NAME:
                            currentGem.setName(tagContent);
                            break;
                        case PRECIOUSNESS:
                            currentGem.setPreciousness(tagContent);
                            break;
                        case ORIGIN:
                            currentGem.setOrigin(tagContent);
                            break;
                        case COLOR:
                            currentGem.setColor(tagContent);
                            break;
                        case TRANSPARENCY:
                            currentGem.setTransparency(Integer.parseInt(tagContent));
                            break;
                        case NUMBER_OF_FACES:
                            currentGem.setNumberOfFaces(Integer.parseInt(tagContent));
                            break;
                        case VALUE:
                            currentGem.setValue(Integer.parseInt(tagContent));
                            break;
                    }
                    break;
                case XMLStreamConstants.START_DOCUMENT:
                    gemList = new ArrayList<>();
                    break;
            }
        }

        return gemList;
    }

    @Override
    public List<Gem> inMemorySax() throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        SaxGemHandler handler = new SaxGemHandler();
        parser.parse("ПЕРЕДАТЬ ПУТЬ ИЛИ ФЙЛ С РЕКУЭСТ", handler);

        return handler.getGems();
    }

    @Override
    public List<Gem> inMemoryDom(Document document) throws ParserConfigurationException, IOException, SAXException {
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        List<Gem> gemList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node instanceof Element) {

                Gem gem = new Gem();
                gem.setId(node.getAttributes().
                        getNamedItem("id").
                        getNodeValue());

                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode instanceof Element) {
                        String content = childNode.getLastChild().getTextContent().trim();
                        switch (childNode.getNodeName()) {
                            case "name":
                                gem.setName(content);
                                break;
                            case "preciousness":
                                gem.setPreciousness(content);
                                break;
                            case "origin":
                                gem.setOrigin(content);
                                break;
                            case "visualParams":
                                parseVisualParams(gem, childNode);
                                break;
                            case "value":
                                gem.setValue(Integer.parseInt(content));
                                break;
                        }
                    }
                }
                gemList.add(gem);
            }
        }

        return gemList;
    }

    private void parseVisualParams(Gem gem, Node childNode) {
        String content;
        NodeList visualParamsChildNodes = childNode.getChildNodes();
        for (int i = 0; i < visualParamsChildNodes.getLength(); i++) {
            Node visualParamsChildNode = visualParamsChildNodes.item(i);
            if (visualParamsChildNode instanceof Element) {
                content = visualParamsChildNode.getLastChild().getTextContent().trim();
                switch (visualParamsChildNode.getNodeName()) {
                    case "color":
                        gem.setColor(content);
                        break;
                    case "transparency":
                        gem.setTransparency(Integer.parseInt(content));
                        break;
                    case "numberOfFaces":
                        gem.setNumberOfFaces(Integer.parseInt(content));
                        break;
                }
            }
        }
    }
}
