package by.training.sokolov.oop.basic_of_oop.task4.builder;

import by.training.sokolov.oop.basic_of_oop.task4.model.Treasure;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class TreasureBuilder {

    private final static List<String> treasureNames = new ArrayList<>(asList(
            "diamond", "gold coin", "crown", "sword", "beer", "vine", "princess",
            "castle", "sport-car", "house", "MacBook-17'", "magic book", "power",
            "bank", "iPhone", "Xiaomi", "IntelliJ license")
    );

    public static Treasure generateRandomTreasure() {
        String name;
        int cost;

        List<String> names = treasureNames;
        int index = (int) (Math.random() * names.size());
        name = names.get(index);

        int maxCost = 100;
        cost = (int) (Math.random() * maxCost);

        Treasure treasure = new Treasure();
        treasure.setName(name);
        treasure.setCost(cost);

        return treasure;
    }
}
