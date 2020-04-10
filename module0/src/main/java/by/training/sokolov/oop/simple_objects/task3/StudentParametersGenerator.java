package by.training.sokolov.oop.simple_objects.task3;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class StudentParametersGenerator {

    public final static List<String> surnames = new ArrayList<>(asList(
            "Kent", "Raven", "Asalia", "Rian", "Andrew", "Discord", "Skype",
            "Bread", "Lanselot", "Petrify", "Rot", "Spoil", "Festerr")
    );

    public static int[] generateMarks() {
        int[] marks = new int[5];
        for (int i = 0; i < marks.length; i++) {
            marks[i] = 9 + ((int) (Math.random() * 2));
        }
        return marks;
    }

    public static String generateGroupNumber() {
        int groupNumber = (((int) (Math.random() * 10)) * 100000) + ((int) (Math.random() * 100));
        return "" + groupNumber;
    }

    public static String generateSurname() {
        List<String> names = surnames;
        int index = (int) (Math.random() * names.size());
        return names.get(index);
    }
}
