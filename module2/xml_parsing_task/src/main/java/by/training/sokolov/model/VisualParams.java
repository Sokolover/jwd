package by.training.sokolov.model;

public class VisualParams {

    private String color;
    private int transparency;
    private int numberOfFaces;

    @Override
    public String toString() {
        return "VisualParams{" +
                "color='" + color + '\'' +
                ", transparency=" + transparency +
                ", numberOfFaces=" + numberOfFaces +
                '}';
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }

    public int getNumberOfFaces() {
        return numberOfFaces;
    }

    public void setNumberOfFaces(int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
    }
}
