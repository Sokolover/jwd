package by.training.sokolov.oop.agregation_and_composition.task3;

import by.training.sokolov.oop.agregation_and_composition.task3.builder.StateBuilder;
import by.training.sokolov.oop.agregation_and_composition.task3.model.State;
import by.training.sokolov.oop.agregation_and_composition.task3.presenrarion.Task3UI;
import by.training.sokolov.oop.agregation_and_composition.task3.service.SimpleStateService;
import by.training.sokolov.oop.agregation_and_composition.task3.service.StateService;

public class Main {

    public static void main(String[] args) {

        Task3UI.showTask();

        State state = StateBuilder.buildState();
        StateService stateService = new SimpleStateService();

        stateService.outputCapital(state);
        stateService.outputRegionAmount(state);
        stateService.outputRegionCenters(state);
        stateService.outputStateSquare(state);
    }


}
