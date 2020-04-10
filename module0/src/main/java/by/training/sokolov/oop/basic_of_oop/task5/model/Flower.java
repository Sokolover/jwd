package by.training.sokolov.oop.basic_of_oop.task5.model;

public class Flower {

    private String color;
    private String size;
    private String name;

    public Flower(String color, String name, String size) {
        this.color = color;
        this.name = name;
        this.size = size;
    }


    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }
}
