package by.training.sokolov.oop.agregation_and_composition.task3.model;

import java.util.List;

public class District {

    private String name;
    private int square;
    private List<City> cityList;

    public District(String name, int square, List<City> cityList) {
        this.name = name;
        this.square = square;
        this.cityList = cityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }
}
