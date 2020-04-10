package by.training.sokolov.oop.agregation_and_composition.task2.service;

import by.training.sokolov.oop.agregation_and_composition.task2.model.Car;
import by.training.sokolov.oop.agregation_and_composition.task2.model.Wheel;
import by.training.sokolov.oop.agregation_and_composition.task2.model.WheelName;
import by.training.sokolov.oop.agregation_and_composition.task2.presentation.Task2UI;

public class SimpleCarService implements CarService {

    public void drive(Car car) {

        System.out.println("Машина будет ехать пока есть топливо.");
        int distance = car.getEngine().generateEnergy(car.getCurrentFlueLevel());
        if (distance < 0) {
            stop();
            return;
        }
        start();
        for (int i = distance; i > 0; i--) {
            System.out.println("У вас осталось энергии на " + i + " км пути");
        }
        stop();
    }

    public void start() {
        System.out.println("Машина начала движение");
    }

    public void stop() {
        System.out.println("Машина остановилась");
    }

    public void fullUp(Car car) {

        if (car.getMaxFlueLevel() == car.getCurrentFlueLevel()) {
            System.out.println("Бак уже полон");
            return;
        }

        System.out.println("Заправляем машну:");
        System.out.println("Текущий уровень топлива: " + car.getCurrentFlueLevel() + " литров");

        int litersToAdd = Task2UI.enterLitersToAdd();
        int currentFlueLevel = car.getCurrentFlueLevel();

        if (car.getMaxFlueLevel() < (currentFlueLevel + litersToAdd)) {
            litersToAdd = car.getMaxFlueLevel() - currentFlueLevel;
        }

        car.setCurrentFlueLevel(currentFlueLevel + litersToAdd);

        System.out.println("Заправлено " + litersToAdd + " литров");
        System.out.println("Текущий уровень топлива: " + car.getCurrentFlueLevel() + " литров");
        if (car.getMaxFlueLevel() == car.getCurrentFlueLevel()) {
            System.out.println("Заправлен полный бак!");
        }
    }


    public void changeWheel(Car car) {

        int wheelNumberToChange = Task2UI.enterWheelNumberToChange();
        WheelName wheelName = WheelName.fromString(Integer.toString(wheelNumberToChange));

        switch (wheelName) {
            case BACK_LEFT:
                System.out.println(WheelName.BACK_LEFT.getWheelRussianName() + " колесо заменено");
                car.getWheelList().set(wheelNumberToChange, new Wheel(WheelName.BACK_LEFT));
                break;
            case BACK_RIGHT:
                System.out.println(WheelName.BACK_RIGHT.getWheelRussianName() + " колесо заменено");
                car.getWheelList().set(wheelNumberToChange, new Wheel(WheelName.BACK_RIGHT));
                break;
            case FRONT_LEFT:
                System.out.println(WheelName.FRONT_LEFT.getWheelRussianName() + " колесо заменено");
                car.getWheelList().set(wheelNumberToChange, new Wheel(WheelName.FRONT_LEFT));
                break;
            case FRONT_RIGHT:
                System.out.println(WheelName.FRONT_RIGHT.getWheelRussianName() + " колесо заменено");
                car.getWheelList().set(wheelNumberToChange, new Wheel(WheelName.FRONT_RIGHT));
                break;
            default:
                throw new NullPointerException("Не задан номер колеса");
        }
    }


}
