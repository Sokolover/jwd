package by.training.sokolov.service;

import by.training.sokolov.model.Gem;

import java.util.ArrayList;
import java.util.List;

public class GemXmlBuilder implements XMLStringBuilder {

    private final Gem gem;

    GemXmlBuilder(Gem gem) {
        this.gem = gem;
    }

    @Override
    public String build() {

        List<XMLStringBuilder> children = new ArrayList<>();
        children.add(new VisualParamsXmlBuilder(gem.getVisualParams()));

        StringBuilder visualParamsStringBuilder = new StringBuilder();
        for (XMLStringBuilder child : children) {
            visualParamsStringBuilder.append(child.build());
        }

        return "<gem id=\"" + gem.getId() + "\">"
                + "<name>"
                + gem.getName()
                + "</name>"
                + "<preciousness>"
                + gem.getPreciousness()
                + "</preciousness>"
                + "<origin>"
                + gem.getOrigin()
                + "</origin>"
                + visualParamsStringBuilder
                + "<value>"
                + gem.getValue()
                + "</value>"
                + "</gem>";
    }
}
