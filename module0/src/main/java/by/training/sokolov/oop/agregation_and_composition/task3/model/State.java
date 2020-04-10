package by.training.sokolov.oop.agregation_and_composition.task3.model;

import java.util.List;

public class State {

    private String name;
    private City capital;
    private List<Region> regionList;

    public State(String name, City capital, List<Region> regionList) {
        this.name = name;
        this.capital = capital;
        this.regionList = regionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCapital() {
        return capital;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }
}
