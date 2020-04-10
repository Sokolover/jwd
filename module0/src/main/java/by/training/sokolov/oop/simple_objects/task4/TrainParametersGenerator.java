package by.training.sokolov.oop.simple_objects.task4;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class TrainParametersGenerator {

    public final static List<String> destinationNames = new ArrayList<>(asList(
            "Minsk", "Brest", "Moscow", "Barcelona", "Berlin", "NY", "Rome", "Paris")
    );

    public static String generateDestinationName() {
        List<String> names = destinationNames;
        int index = (int) (Math.random() * names.size());
        return names.get(index);
    }

    public static String generateTrainNumber() {
        int trainNumber = ((100 * ((int) (Math.random() * 10)) + (int) (Math.random() * 10)));
        return "" + trainNumber;
    }

    public static String generateDepartureTime() {
        int hoursInteger = (int) (Math.random() * 24);
        int minutesInteger = (int) (Math.random() * 60);
        String hoursString = formatTimeElement(hoursInteger);
        String minutesString = formatTimeElement(minutesInteger);

        return hoursString + ":" + minutesString;
    }

    private static String formatTimeElement(int intElement) {
        String stringElement;
        if (intElement < 10) {
            stringElement = "0" + intElement;
        } else {
            stringElement = "" + intElement;
        }
        return stringElement;
    }
}
