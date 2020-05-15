package by.training.sokolov.oop.basic_of_oop.task4.presentation;

import by.training.sokolov.oop.basic_of_oop.task4.model.Treasure;

import java.util.List;
import java.util.Scanner;

public class TreasureUI {

    public static void showTask() {
        System.out.println("Дракон и его сокровища.\n" +
                "Создать программу, позволяющую обрабатывать сведения о 100 сокровищах в пещере дракона.\n" +
                "Реализовать возможность\n" +
                "- просмотра сокровищ,\n" +
                "- выбора самого дорогого по стоимости сокровища\n" +
                "- выбора сокровищ на заданную сумму.\n");
    }

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static void outputTreasureList(List<Treasure> treasureList) {
        if (treasureList.isEmpty()) {
            System.out.println("Вам не хватило денег на сокровища :(");
        } else {
            System.out.println("Название\t\t\t\tСтоимость");
            System.out.println("________________________________");
            for (Treasure treasure : treasureList) {
                outputTreasure(treasure);
            }
        }
    }

    public static void outputTreasure(Treasure treasure) {
        System.out.printf("%-20s", treasure.getName());
        System.out.printf("%6s руб.", Integer.toString(treasure.getCost()));
        System.out.println();
    }

    public static int enterUserSum() {
        System.out.println("Введите сумму, на которую вы хотите приобрести сокровища:");
        return enterInt();
    }

    public static int enterTreasureAmount() {
        System.out.println("Введите количество сокровищ в пещере дракона:");
        return enterInt();
    }
}
