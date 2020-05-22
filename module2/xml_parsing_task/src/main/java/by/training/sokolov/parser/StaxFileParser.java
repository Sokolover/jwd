package by.training.sokolov.parser;

import by.training.sokolov.model.Gem;
import by.training.sokolov.model.GemEnum;
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

public class StaxFileParser implements FileParser<Gem> {

    private final static Logger LOGGER = Logger.getLogger(StaxFileParser.class.getName());

    @Override
    public ParseResult<Gem> parse(String filePath) throws XMLStreamException {

        LOGGER.info("*** StAX parser works ***");

        List<Gem> gems = null;
        Gem currentGem = null;
        String tagContent = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
            return new ParseResult<>(new ArrayList<>(), e.getMessage());
        }

        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (GemEnum.fromString(reader.getLocalName()) == GemEnum.GEM) {
                        currentGem = new Gem();
                        currentGem.setId(Long.parseLong(reader.getAttributeValue(0)));
                    }
                    if (GemEnum.fromString(reader.getLocalName()) == GemEnum.GEMS) {
                        gems = new ArrayList<>();
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    tagContent = reader.getText().trim();
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    switch (GemEnum.fromString(reader.getLocalName())) {
                        case GEM:
                            gems.add(currentGem);
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
                    gems = new ArrayList<>();
                    break;
            }
        }

        String message = "successful Stax parsing";
        ParseResult<Gem> result = new ParseResult<>(gems, message);
        return result;
    }
}
