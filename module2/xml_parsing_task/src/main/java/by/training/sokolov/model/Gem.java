package by.training.sokolov.model;

import java.util.Objects;

public class Gem implements Cloneable {

    private Long id;
    private String name;
    private String preciousness;
    private String origin;
    private VisualParams visualParams;
    private int value;

    public Gem() {
        visualParams = new VisualParams();
    }

    public Gem(Long id, String name, String preciousness, String origin, VisualParams visualParams, int value) {
        this.id = id;
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
        this.visualParams = visualParams;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Gem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", preciousness='" + preciousness + '\'' +
                ", origin='" + origin + '\'' +
                ", visualParams=" + visualParams +
                ", value=" + value +
                '}';
    }

    public VisualParams getVisualParams() {
        return visualParams;
    }

    public void setVisualParams(VisualParams visualParams) {
        this.visualParams = visualParams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreciousness() {
        return preciousness;
    }

    public void setPreciousness(String preciousness) {
        this.preciousness = preciousness;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getColor() {
        return visualParams.getColor();
    }

    public void setColor(String color) {
        this.visualParams.setColor(color);
    }

    public int getTransparency() {
        return visualParams.getTransparency();
    }

    public void setTransparency(int transparency) {
        this.visualParams.setTransparency(transparency);
    }

    public int getNumberOfFaces() {
        return visualParams.getNumberOfFaces();
    }

    public void setNumberOfFaces(int numberOfFaces) {
        this.visualParams.setNumberOfFaces(numberOfFaces);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gem gem = (Gem) o;
        return value == gem.value &&
                Objects.equals(id, gem.id) &&
                Objects.equals(name, gem.name) &&
                Objects.equals(preciousness, gem.preciousness) &&
                Objects.equals(origin, gem.origin) &&
                Objects.equals(visualParams, gem.visualParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, preciousness, origin, visualParams, value);
    }

    public Gem clone() throws CloneNotSupportedException {

        return (Gem) super.clone();
    }
}
