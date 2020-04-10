package by.training.sokolov.oop.agregation_and_composition.task2;

import by.training.sokolov.oop.agregation_and_composition.task2.builder.CarBuilder;
import by.training.sokolov.oop.agregation_and_composition.task2.model.Car;
import by.training.sokolov.oop.agregation_and_composition.task2.presentation.Task2UI;
import by.training.sokolov.oop.agregation_and_composition.task2.service.CarService;
import by.training.sokolov.oop.agregation_and_composition.task2.service.SimpleCarService;

import static by.training.sokolov.oop.agregation_and_composition.task2.presentation.Task2UI.showTask;

//2. Создать объект класса Автомобиль, " +
//        "используя классы Колесо, Двигатель. " +
//        "Методы: " +
//        " - ехать, " +
//        " - заправляться, " +
//        " - менять колесо, " +
//        " - вывести на консоль марку автомобиля.

public class Main {

    public static void main(String[] args) {

        showTask();

        Car car = CarBuilder.buildCar();

        CarService simpleCarService = new SimpleCarService();

        simpleCarService.fullUp(car);
        simpleCarService.drive(car);
        simpleCarService.changeWheel(car);
        Task2UI.outputCarModel(car);
    }
}
