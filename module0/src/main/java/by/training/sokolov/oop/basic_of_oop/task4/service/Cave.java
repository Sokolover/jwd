package by.training.sokolov.oop.basic_of_oop.task4.service;

import by.training.sokolov.oop.basic_of_oop.task4.model.Treasure;
import by.training.sokolov.oop.basic_of_oop.task4.service.CaveSelectionService;

import java.util.ArrayList;
import java.util.List;

import static by.training.sokolov.oop.basic_of_oop.task4.presentation.TreasureUI.outputTreasure;
import static by.training.sokolov.oop.basic_of_oop.task4.presentation.TreasureUI.outputTreasureList;

public class Cave implements CaveSelectionService {

    public Cave() {

    }

    @Override
    public void selectTreasureBySum(int userSum, List<Treasure> treasureList) {
        System.out.println("Сокровища на сумму " + userSum + " руб.:");
        List<Treasure> selectedTreasureList = createSelectedTreasureList(userSum, treasureList);
        System.out.println();
        outputTreasureList(selectedTreasureList);
    }

    private List<Treasure> createSelectedTreasureList(int userSum, List<Treasure> treasureList) {
        List<Treasure> selectedTreasureList = new ArrayList<>();
        int treasureSum = 0;
        for (Treasure treasure : treasureList) {
            if ((treasure.getCost() + treasureSum) < userSum) {
                treasureSum += treasure.getCost();
                selectedTreasureList.add(treasure);
            } else if (treasureSum >= userSum) {
                break;
            }
        }
        return selectedTreasureList;
    }

    @Override
    public void selectExpensiveTreasure(List<Treasure> treasureList) {
        System.out.println("\nСамое(ые) ценное(ые) сокровище(а):");
        Treasure mostExpensive = treasureList.get(0);
        mostExpensive = findMostExpensiveTreasure(mostExpensive, treasureList);
        outputMostExpensive(mostExpensive, treasureList);
        System.out.println();
    }

    private Treasure findMostExpensiveTreasure(Treasure mostExpensive, List<Treasure> treasureList) {
        for (Treasure treasure : treasureList) {
            if (treasure.getCost() > mostExpensive.getCost()) {
                mostExpensive = treasure;
            }
        }
        return mostExpensive;
    }

    private void outputMostExpensive(Treasure mostExpensive, List<Treasure> treasureList) {
        for (Treasure treasure : treasureList) {
            if (mostExpensive.getCost() == treasure.getCost()) {
                outputTreasure(treasure);
            }
        }
    }

}
