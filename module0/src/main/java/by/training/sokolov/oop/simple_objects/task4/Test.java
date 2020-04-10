package by.training.sokolov.oop.simple_objects.task4;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        task4();
    }

    static void task4() {
        System.out.println("4. Создайте класс Train, содержащий поля: \n" +
                "- название пункта назначения, \n" +
                "- номер поезда, \n" +
                "- время отправления. \n" +
                "Создайте данные в массив из пяти элементов типа Train, \n" +
                "1) Добавьте возможность сортировки элементов массива по номерам поездов. \n" +
                "2) Добавьте возможность вывода информации о поезде, \n" +
                "номер которого введен пользователем. \n" +
                "3) Добавьте возможность сортировки массива по пункту назначения, \n" +
                "причем поезда с одинаковыми пунктами назначения должны быть упорядочены по времени отправления.\n");

        Train[] trains = createTrainArray();

        sortTrainArrayByNumber(trains);
        sortTrainArrayByDestination(trains);
        outputTrainByNumber(trains);
    }

    private static Train[] createTrainArray() {
        Train[] trains = new Train[5];

        for (int i = 0; i < trains.length; i++) {
            Train newStudent = new Train();
            trains[i] = newStudent;
        }
        outputTrainArray(trains);
        return trains;
    }

    private static void outputTrainByNumber(Train[] trains) {
        Scanner in = new Scanner(System.in);
        System.out.println("\nВведите номер поезда, который Вы ищите: ");
        String usersTrainNumber = in.nextLine();

        boolean trainNotExist = true;
        for (Train train : trains) {
            if (train.getTrainNumber().equals(usersTrainNumber)) {
                System.out.println("Поезд найден:");
                train.outputTrainInfo();
                trainNotExist = false;
            }
        }
        if (trainNotExist) {
            System.out.println("Поезд не найден.");
        }
    }

    public static void sortTrainArrayByDestination(Train[] array) {
        System.out.println("\n\nSort Train Array By Destination and department time: ");
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[j].getDestinationName().compareTo(array[i].getDestinationName()) < 0) {
                    swapTrains(array, i, j);
                }
                if (array[j].getDestinationName().equals(array[i].getDestinationName())) {
                    if (array[j].getDepartureTime().compareTo(array[i].getDepartureTime()) < 0) {
                        swapTrains(array, i, j);
                    }
                }
            }
        }
        outputTrainArray(array);
    }

    public static void sortTrainArrayByNumber(Train[] array) {
        System.out.println("\n\nSort Train Array By Number: ");
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[j].getTrainNumber().compareTo(array[i].getTrainNumber()) < 0) {
                    swapTrains(array, i, j);
                }
            }
        }
        outputTrainArray(array);
    }

    private static void swapTrains(Train[] array, int i, int j) {
        Train temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    static void outputTrainArray(Train[] trainArray) {
        System.out.println("\n");
        for (Train train : trainArray) {
            train.outputTrainInfo();
        }
    }
}
