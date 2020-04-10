package by.training.sokolov.oop.basic_of_oop.task5.builder;

import by.training.sokolov.oop.basic_of_oop.task5.model.Flower;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class FlowerBuilder {

    private final static List<String> nameList = new ArrayList<>(asList(
            "rose", "aster", "begonia", "cornflower", "carnation",
            "geranium", "herbera", "glycinia", "iris", "lavender", "lily", "poppy")
    );

    private final static List<String> sizeList = new ArrayList<>(asList(
            "big", "middle", "small")
    );

    private final static List<String> colorList = new ArrayList<>(asList(
            "red", "blue", "green", "yellow", "purple", "pink", "white", "black")
    );

    public static Flower generateRandomFlower() {

        int colorIndex = (int) (Math.random() * colorList.size());
        int nameIndex = (int) (Math.random() * nameList.size());
        int sizeIndex = (int) (Math.random() * sizeList.size());

        String color = colorList.get(colorIndex);
        String name = nameList.get(nameIndex);
        String size = sizeList.get(sizeIndex);

        return new Flower(color, name, size);
    }
}
