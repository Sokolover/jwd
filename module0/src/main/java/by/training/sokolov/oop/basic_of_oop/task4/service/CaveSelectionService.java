package by.training.sokolov.oop.basic_of_oop.task4.service;

import by.training.sokolov.oop.basic_of_oop.task4.model.Treasure;

import java.util.List;

public interface CaveSelectionService {

    void selectTreasureBySum(int userSum, List<Treasure> treasureList);

    void selectExpensiveTreasure(List<Treasure> treasureList);
}
