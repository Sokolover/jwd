package by.training.sokolov.service;

import by.training.sokolov.model.VisualParams;

public class VisualParamsXmlBuilder implements XMLStringBuilder {

    private final VisualParams visualParams;

    VisualParamsXmlBuilder(VisualParams visualParams) {
        this.visualParams = visualParams;
    }

    @Override
    public String build() {

        return "<visualParams>"
                + "<color>"
                + visualParams.getColor()
                + "</color>"
                + "<transparency>"
                + visualParams.getTransparency()
                + "</transparency>"
                + "<numberOfFaces>"
                + visualParams.getNumberOfFaces()
                + "</numberOfFaces>"
                + "</visualParams>";
    }
}
