package by.training.sokolov.oop.agregation_and_composition.task3.service;

import by.training.sokolov.oop.agregation_and_composition.task3.model.District;
import by.training.sokolov.oop.agregation_and_composition.task3.model.Region;
import by.training.sokolov.oop.agregation_and_composition.task3.model.State;

public class SimpleStateService implements StateService {
    //    Методы:
//             - вывести на консоль:
//             - столицу,
//             - количество областей,
//             - площадь,
//             - областные центры.

    @Override
    public void outputCapital(State state) {
        System.out.println("Столица государства: " + state.getCapital().getName());
    }

    @Override
    public void outputRegionAmount(State state) {
        int regionCounter = 0;
        for (int i = 0; i < state.getRegionList().size(); i++) {
            regionCounter++;
        }
        System.out.println("Количество областей: " + regionCounter);
    }

    @Override
    public void outputStateSquare(State state) {
        int stateSquare = 0;
        for (Region region : state.getRegionList()) {
            for (District district : region.getDistrictList()) {
                stateSquare += district.getSquare();
            }
        }
        System.out.println("Площадь государства: " + stateSquare);
    }

    public void outputRegionCenters(State state) {
        for (Region region : state.getRegionList()) {
            System.out.println("Областной центр " + region.getName() + "-ой области: " + region.getRegionCenter().getName());
        }
    }
}
