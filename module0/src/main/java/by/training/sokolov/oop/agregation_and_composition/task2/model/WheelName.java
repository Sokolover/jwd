package by.training.sokolov.oop.agregation_and_composition.task2.model;

public enum WheelName {

    FRONT_LEFT("0", "переднее левое"),
    FRONT_RIGHT("1", "переднее правое"),
    BACK_LEFT("2", "заднее левое"),
    BACK_RIGHT("3", "заднее правое");

    private String wheelNumber;
    private String wheelRussianName;
            
    WheelName(String number, String russianName) {
        this.wheelNumber = number;
        this.wheelRussianName = russianName;
    }

    public static WheelName fromString(String name) {

        final WheelName[] values = WheelName.values();
        for (WheelName wheelName : values) {
            if (wheelName.wheelNumber.equals(name) || wheelName.name().equals(name)) {
                return wheelName;
            }
        }
        return null;
    }

    public String getWheelRussianName() {
        return wheelRussianName;
    }

}
