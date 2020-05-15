package by.training.sokolov.oop.agregation_and_composition.task2.model;

public class Wheel {

    private WheelName wheelName;

    public Wheel(WheelName wheelName) {
        this.wheelName = wheelName;
    }

    public WheelName getWheelName() {
        return wheelName;
    }

    public void setWheelName(WheelName wheelName) {
        this.wheelName = wheelName;
    }
}
