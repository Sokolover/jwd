package by.training.sokolov.oop.agregation_and_composition.task3.builder;

import by.training.sokolov.oop.agregation_and_composition.task3.model.City;
import by.training.sokolov.oop.agregation_and_composition.task3.model.District;
import by.training.sokolov.oop.agregation_and_composition.task3.presenrarion.Task3UI;

import java.util.ArrayList;
import java.util.List;

class DistrictBuilder {

    static District buildDistrict() {

        System.out.println("Введите название района:");
        String name = Task3UI.enterName();
        System.out.println("Введите площадь:");
        int square = Task3UI.enterAmount();
        List<City> cityList = generateCityList();

        return new District(name, square, cityList);
    }

    private static List<City> generateCityList() {

        System.out.println("Теперь заполним район городами");
        System.out.println("Введите количество городов:");
        int cityAmount = Task3UI.enterAmount();
        List<City> cityList = new ArrayList<>();

        for (int i = 0; i < cityAmount; i++) {
            cityList.add(CityBuilder.buildCity());
        }

        return cityList;
    }
}
