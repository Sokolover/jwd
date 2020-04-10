package by.training.sokolov.oop.agregation_and_composition.task3.builder;

import by.training.sokolov.oop.agregation_and_composition.task3.model.City;
import by.training.sokolov.oop.agregation_and_composition.task3.presenrarion.Task3UI;

class CityBuilder {

    static City buildCity() {

        System.out.println("Создаём город");
        System.out.println("Введите название города:");
        String name = Task3UI.enterName();

        return new City(name);
    }
}
