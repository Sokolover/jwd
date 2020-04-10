package by.training.sokolov.oop.simple_objects.task4;

public class Train {
    private String destinationName;
    private String trainNumber;
    private String departureTime;

    public Train() {
        this.destinationName = TrainParametersGenerator.generateDestinationName();
        this.trainNumber = TrainParametersGenerator.generateTrainNumber();
        this.departureTime = TrainParametersGenerator.generateDepartureTime();
    }

    public void outputTrainInfo() {
        System.out.println("\ndestination name:\t" + this.getDestinationName());
        System.out.println("train number:\t\t" + this.getTrainNumber());
        System.out.println("departure time:\t\t" + this.getDepartureTime());
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
