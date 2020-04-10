package by.training.sokolov.oop.agregation_and_composition.task2.presentation;

import by.training.sokolov.oop.agregation_and_composition.task2.model.Car;

import java.util.Scanner;

public class Task2UI {
    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static void outputEngineInfo(int flue, int coefficient, int distance) {
        System.out.println("Эффективность топлива: " + coefficient);
        System.out.println("Литров топлива, которое двигатель будет использовать: " + flue);
        System.out.println("Длина поездки = Эффективность топлива * Литров топлива");
        System.out.println("Количество энергии, которое выработает двигатель, хватит на поездку длиной " + distance + " км");
    }

    public static void showTask() {
        System.out.println("2. Создать объект класса Автомобиль, " +
                "используя классы Колесо, Двигатель. " +
                "Методы: " +
                " - ехать, " +
                " - заправляться, " +
                " - менять колесо, " +
                " - вывести на консоль марку автомобиля.");
    }

    public static int enterLitersToAdd() {
        System.out.println("Сколько залить?");
        return enterInt();
    }

    public static int enterMaxFlueLevel() {
        System.out.println("Введите желаемый объём бака");
        return enterInt();
    }

    public static String enterCarModel() {
        System.out.println("Введите модель машины:");
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public static void outputCarModel(Car car) {
        System.out.println("Модель машины:\t" + car.getCarModel());
    }

    public static int enterWheelNumberToChange() {
        System.out.println("Введите номер колеса, которое надо заменить");
        System.out.println("0 - переднее левое\n" +
                "1 - переднее правое\n" +
                "2 - заднее левое\n" +
                "3 - заднее правое\n");
        int choice;
        do {
            choice = enterInt();
        } while (choice < 0 || choice > 3);
        return choice;
    }
}
