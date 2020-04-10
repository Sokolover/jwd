package by.training.sokolov.oop.agregation_and_composition.task3.builder;

import by.training.sokolov.oop.agregation_and_composition.task3.model.City;
import by.training.sokolov.oop.agregation_and_composition.task3.model.District;
import by.training.sokolov.oop.agregation_and_composition.task3.model.Region;
import by.training.sokolov.oop.agregation_and_composition.task3.model.State;
import by.training.sokolov.oop.agregation_and_composition.task3.presenrarion.Task3UI;

import java.util.ArrayList;
import java.util.List;

public class StateBuilder {

    private static List<Region> generateRegionList() {

        System.out.println("Теперь заполним список областей в государстве");
        System.out.println("Введите количество областей в государстве:");
        int regionAmount = Task3UI.enterAmount();
        List<Region> regionList = new ArrayList<>();

        for (int i = 0; i < regionAmount; i++) {
            regionList.add(RegionBuilder.buildRegion());
        }

        return regionList;
    }

    private static City chooseCapital(List<Region> regionList) {

        boolean isInSearch = true;

        while (isInSearch) {
            System.out.println("Выберите, какой город будет являться столицей:");
            String name = Task3UI.enterName();

            for (Region region : regionList) {
                for (District district : region.getDistrictList()) {
                    for (City city : district.getCityList()) {
                        if (city.getName().equalsIgnoreCase(name)) {
                            return city;
                        }
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

    public static State buildState() {

        System.out.println("Введите название государства");
        String name = Task3UI.enterName();
        List<Region> regionList = generateRegionList();
        City capital = chooseCapital(regionList);

        return new State(name, capital, regionList);
    }

}
