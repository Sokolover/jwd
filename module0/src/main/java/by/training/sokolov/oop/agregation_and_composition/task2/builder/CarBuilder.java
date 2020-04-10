package by.training.sokolov.oop.agregation_and_composition.task2.builder;

import by.training.sokolov.oop.agregation_and_composition.task2.model.Car;
import by.training.sokolov.oop.agregation_and_composition.task2.model.Engine;
import by.training.sokolov.oop.agregation_and_composition.task2.model.Wheel;
import by.training.sokolov.oop.agregation_and_composition.task2.model.WheelName;
import by.training.sokolov.oop.agregation_and_composition.task2.presentation.Task2UI;

import java.util.ArrayList;
import java.util.List;

public class CarBuilder {

    private static final int WHEEL_AMOUNT = 4;

    public static Car buildCar() {

        Engine engine = new Engine();

        List<Wheel> wheelList = createWheelList();

        String carModel = Task2UI.enterCarModel();
        int maxFlueLevel = Task2UI.enterMaxFlueLevel();
        int currentFlueLevel = 0;

        return new Car(engine, carModel, maxFlueLevel, currentFlueLevel, wheelList);
    }

    private static List<Wheel> createWheelList() {

        System.out.println("У вашей машины будет " + WHEEL_AMOUNT + " колеса");
        List<Wheel> wheelList = new ArrayList<>();
        final WheelName[] values = WheelName.values();

        for (int i = 0; i < WHEEL_AMOUNT; i++) {
            wheelList.add(new Wheel(values[i]));
        }
        return wheelList;
    }
}
