package by.training.sokolov.service;

import by.training.sokolov.model.Gem;

import java.util.List;

public class GemsXmlBuilder implements XMLStringBuilder {

    private final List<Gem> gems;

    public GemsXmlBuilder(List<Gem> gems) {
        this.gems = gems;
    }

    @Override
    public String build() {

        StringBuilder gemsStringBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                "<gems xmlns=\"http://sokolov.com/gem\"\n" +
                "      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "      xsi:schemaLocation=\"http://sokolov.com/gem gemSchema.xsd\">");

        for (Gem gem : gems) {
            gemsStringBuilder.append(new GemXmlBuilder(gem).build());
        }
        gemsStringBuilder.append("</gems>");

        return new String(gemsStringBuilder);
    }
}
