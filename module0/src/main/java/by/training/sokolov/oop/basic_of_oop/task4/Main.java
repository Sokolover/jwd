package by.training.sokolov.oop.basic_of_oop.task4;

import by.training.sokolov.oop.basic_of_oop.task4.builder.TreasureBuilder;
import by.training.sokolov.oop.basic_of_oop.task4.model.Treasure;
import by.training.sokolov.oop.basic_of_oop.task4.service.Cave;
import by.training.sokolov.oop.basic_of_oop.task4.service.CaveSelectionService;

import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.oop.basic_of_oop.task4.presentation.TreasureUI.*;

public class Main {

    public static void main(String[] args) {

        showTask();
        List<Treasure> treasureList = getTreasures();

        outputTreasureList(treasureList);

        CaveSelectionService caveSelectionService = new Cave();
        caveSelectionService.selectExpensiveTreasure(treasureList);
        caveSelectionService.selectTreasureBySum(enterUserSum(), treasureList);
    }

    private static List<Treasure> getTreasures() {
        List<Treasure> treasureList = new ArrayList<>();

        int treasureAmount = enterTreasureAmount();
        for (int i = 0; i < treasureAmount; i++) {
            treasureList.add(TreasureBuilder.generateRandomTreasure());
        }
        return treasureList;
    }
}
