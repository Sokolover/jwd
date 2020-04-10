package by.training.sokolov.oop.agregation_and_composition.task3.presenrarion;

import java.util.Scanner;

public class Task3UI {

    public static void showTask() {
        System.out.println("3. Создать объект класса Государство, \n" +
                "используя классы Область, Район, Город. \n" +
                "Методы: \n" +
                " - вывести на консоль:\n" +
                " - столицу, \n" +
                " - количество областей, \n" +
                " - площадь, \n" +
                " - областные центры.\n");
    }

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static String enterName() {
        System.out.println("");
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public static int enterSquare() {

        return enterInt();
    }

    public static int enterAmount() {
        System.out.println("");
        return enterInt();
    }

    public static int enterAnswerDigit() {
        int answer;
        do {
            answer = enterInt();
        } while (answer < 1 || answer > 2);
        return enterInt();
    }


}
