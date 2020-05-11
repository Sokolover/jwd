package by.training.sokolov.task3.controller.parsers;

import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.model.GemEnum;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.task3.model.GemEnum.GEM;
import static by.training.sokolov.task3.model.GemEnum.GEMS;

public class StAXGemParser {

    private final static Logger LOGGER = Logger.getLogger(StAXGemParser.class.getName());

    public List<Gem> parse(String path) throws XMLStreamException {

        List<Gem> gemList = null;
        Gem currentGem = null;
        String tagContent = null;

        //todo сделать чтобы читалось из String path а не из "xml/gem.xml" !!!
        XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(path));
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

}
