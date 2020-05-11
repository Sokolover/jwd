package by.training.sokolov.task3.controller.parsers;

import by.training.sokolov.task3.model.Gem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DOMGemParser {

    public List<Gem> parse(String path) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path);
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
