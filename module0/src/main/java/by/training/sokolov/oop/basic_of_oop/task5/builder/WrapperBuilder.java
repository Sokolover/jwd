package by.training.sokolov.oop.basic_of_oop.task5.builder;

import by.training.sokolov.oop.basic_of_oop.task5.model.Wrapper;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class WrapperBuilder {

    private final static List<String> colorList = new ArrayList<>(asList(
            "red", "blue", "green", "yellow", "purple", "pink", "white", "black")
    );

    private final static List<String> typeList = new ArrayList<>(asList(
            "paper", "cardboard", "cellophane", "flower basket", "ribbon", "elastic band")
    );

    public static Wrapper generateRandomWrapper() {

        int colorIndex = (int) (Math.random() * colorList.size());
        int typeIndex = (int) (Math.random() * typeList.size());

        String color = colorList.get(colorIndex);
        String type = typeList.get(typeIndex);

        return new Wrapper(color, type);
    }

}
