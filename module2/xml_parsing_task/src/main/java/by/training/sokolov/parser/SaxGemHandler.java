package by.training.sokolov.parser;

import by.training.sokolov.model.Gem;
import by.training.sokolov.model.GemEnum;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static by.training.sokolov.model.GemEnum.GEM;
import static by.training.sokolov.model.GemEnum.ID;

public class SaxGemHandler extends DefaultHandler {

    private List<Gem> gems;
    private Gem currentGem;
    private GemEnum currentEnum;
    private EnumSet<GemEnum> withText;

    SaxGemHandler() {
        this.gems = new ArrayList<>();
        withText = EnumSet.range(GemEnum.PRECIOUSNESS, GemEnum.VALUE);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (GEM.getValue().equals(qName)) {
            currentGem = new Gem();
            Long id = Long.parseLong(attributes.getValue(ID.getValue()));
            currentGem.setId(id);
        } else {
            GemEnum temp = GemEnum.fromString(qName);
            if (withText.contains(temp)) {
                currentEnum = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (GEM.getValue().equals(qName)) {
            gems.add(currentGem);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {

        String content = new String(ch, start, length).trim();
        if (currentEnum != null) {
            switch (currentEnum) {
                case NAME:
                    currentGem.setName(content);
                    break;
                case PRECIOUSNESS:
                    currentGem.setPreciousness(content);
                    break;
                case ORIGIN:
                    currentGem.setOrigin(content);
                    break;
                case COLOR:
                    currentGem.setColor(content);
                    break;
                case TRANSPARENCY:
                    currentGem.setTransparency(Integer.parseInt(content));
                    break;
                case NUMBER_OF_FACES:
                    currentGem.setNumberOfFaces(Integer.parseInt(content));
                    break;
                case VALUE:
                    currentGem.setValue(Integer.parseInt(content));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentEnum.getDeclaringClass(),
                            currentEnum.name());
            }
            currentEnum = null;
        }
    }

    List<Gem> getGems() {
        return gems;
    }

}
