package by.training.sokolov.parser;

import by.training.sokolov.model.Gem;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DomFileParser implements FileParser<Gem> {

    private final static Logger LOGGER = Logger.getLogger(DomFileParser.class.getName());

    @Override
    public ParseResult<Gem> parse(String filePath) throws ParseException {

        LOGGER.info("*** DOM parser works ***");

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            String msg = "cannot find file";
            LOGGER.error(msg, e);
            return new ParseResult<>(new ArrayList<>(), msg);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document;

        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            String msg = "newDocumentBuilder() exception in " + this.getClass();
            LOGGER.error(msg, e);
            return new ParseResult<>(new ArrayList<>(), msg);
        }

        try {
            document = builder.parse(inputStream);
        } catch (SAXException e) {
            String msg = "SAXException while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return new ParseResult<>(new ArrayList<>(), msg);
        } catch (IOException e) {
            String msg = "IOException while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return new ParseResult<>(new ArrayList<>(), msg);
        } catch (Exception e) {
            String msg = "Exception while parsing inputStream in " + this.getClass();
            LOGGER.error(msg, e);
            return new ParseResult<>(new ArrayList<>(), msg);
        }

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        List<Gem> gems = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node instanceof Element) {

                Gem gem = new Gem();
                gem.setId(Long.parseLong(
                        node.getAttributes().
                                getNamedItem("id").
                                getNodeValue()));

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
                gems.add(gem);
            }
        }

        String msg = "successful Dom parsing";
        return new ParseResult<>(gems, msg);
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
