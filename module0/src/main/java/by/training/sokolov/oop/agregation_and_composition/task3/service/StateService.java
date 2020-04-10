package by.training.sokolov.oop.agregation_and_composition.task3.service;

import by.training.sokolov.oop.agregation_and_composition.task3.model.State;

public interface StateService {

    void outputRegionAmount(State state);

    void outputCapital(State state);

    void outputStateSquare(State state);

    void outputRegionCenters(State state);
}
