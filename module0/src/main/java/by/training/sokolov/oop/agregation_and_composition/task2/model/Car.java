package by.training.sokolov.oop.agregation_and_composition.task2.model;

import java.util.List;

public class Car {

    private Engine engine;
    private List<Wheel> wheelList;
    private String carModel;
    private int maxFlueLevel;
    private int currentFlueLevel;

    public Car(Engine engine, String carModel, int maxFlueLevel, int currentFlueLevel, List<Wheel> wheelList) {
        this.engine = engine;
        this.wheelList = wheelList;
        this.carModel = carModel;
        this.maxFlueLevel = maxFlueLevel;
        this.currentFlueLevel = currentFlueLevel;
    }

    public int getMaxFlueLevel() {
        return maxFlueLevel;
    }


    public int getCurrentFlueLevel() {
        return currentFlueLevel;
    }

    public void setCurrentFlueLevel(int currentFlueLevel) {
        this.currentFlueLevel = currentFlueLevel;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public List<Wheel> getWheelList() {
        return wheelList;
    }

    public void setWheelList(List<Wheel> wheelList) {
        this.wheelList = wheelList;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
