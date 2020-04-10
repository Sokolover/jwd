package by.training.sokolov.oop.agregation_and_composition.task2.service;

import by.training.sokolov.oop.agregation_and_composition.task2.model.Car;

public interface CarService {

    void start();

    void stop();

    void drive(Car car);

    void fullUp(Car car);

    void changeWheel(Car car);

}
