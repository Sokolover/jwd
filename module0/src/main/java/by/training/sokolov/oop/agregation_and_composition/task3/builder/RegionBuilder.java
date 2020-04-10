package by.training.sokolov.oop.agregation_and_composition.task3.builder;

import by.training.sokolov.oop.agregation_and_composition.task3.model.City;
import by.training.sokolov.oop.agregation_and_composition.task3.model.District;
import by.training.sokolov.oop.agregation_and_composition.task3.model.Region;
import by.training.sokolov.oop.agregation_and_composition.task3.presenrarion.Task3UI;

import java.util.ArrayList;
import java.util.List;

class RegionBuilder {

    static Region buildRegion() {

        System.out.println("Введите имя области:");
        String name = Task3UI.enterName();
        List<District> districtList = generateDistrictList();
        City regionCenter = chooseRegionCenter(districtList);

        return new Region(regionCenter, name, districtList);
    }

    private static City chooseRegionCenter(List<District> districtList) {

        boolean isInSearch = true;

        while (isInSearch) {
            System.out.println("Выберите, какой город будет являться районным центорм:");
            String name = Task3UI.enterName();

            for (District district : districtList) {
                for (City city : district.getCityList()) {
                    if (city.getName().equalsIgnoreCase(name)) {
                        return city;
                    }
                }
            }

            System.out.println("Такого города нет. Хотите попробовать выбрать ещё раз?\n" +
                    "1 - да\n" +
                    "2 - нет\n");
            if (Task3UI.enterAnswerDigit() == 2) {
                isInSearch = false;
            }
        }
        return null;
    }

    private static List<District> generateDistrictList() {

        System.out.println("Теперь заполним список районов в области");
        System.out.println("Введите количество районов:");
        int districtAmount = Task3UI.enterAmount();
        List<District> districtList = new ArrayList<>();

        for (int i = 0; i < districtAmount; i++) {
            districtList.add(DistrictBuilder.buildDistrict());
        }

        return districtList;
    }
}
