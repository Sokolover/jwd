package by.training.sokolov.oop.agregation_and_composition.task2.model;

import by.training.sokolov.oop.agregation_and_composition.task2.presentation.Task2UI;

public class Engine {

    public int generateEnergy(int flue) {

        int coefficient = 5;
        int distance = flue * coefficient;
        Task2UI.outputEngineInfo(flue, coefficient, distance);

        return distance;
    }


}
