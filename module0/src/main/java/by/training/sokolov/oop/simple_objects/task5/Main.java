package by.training.sokolov.oop.simple_objects.task5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("5. Опишите класс, реализующий десятичный счетчик, \n" +
                "который может увеличивать или уменьшать свое значение на единицу в заданном диапазоне. \n" +
                "Предусмотрите инициализацию счетчика значениями по умолчанию и произвольными значениями. \n" +
                "Счетчик имеет методы увеличения и уменьшения состояния, и метод позволяющее получить его текущее состояние. \n" +
                "Написать код, демонстрирующий все возможности класса.\n");

        SimpleCounterService simpleCounterService = new SimpleCounterService();

        DecimalCounter decimalCounter1 = buildDefaultDecimalCounter();
        DecimalCounter decimalCounter2 = buildUserDecimalCounter();

        int step = 1;

        System.out.println("Увеличение и уменьшение значения для счётчика по умолчанию");
        simpleCounterService.incrementValue(decimalCounter1, step);
        simpleCounterService.getCurrentCounterCondition(decimalCounter1);
        System.out.println("уменьшаем значение счётчика 2 раза...");
        simpleCounterService.decrementValue(decimalCounter1, step);
        simpleCounterService.decrementValue(decimalCounter1, step);
        simpleCounterService.getCurrentCounterCondition(decimalCounter1);

        System.out.println("Увеличение и уменьшение значения для счётчика пользователя");
        simpleCounterService.incrementValue(decimalCounter2, step);
        simpleCounterService.getCurrentCounterCondition(decimalCounter2);
        System.out.println("уменьшаем значение счётчика 2 раза...");
        simpleCounterService.decrementValue(decimalCounter2, step);
        simpleCounterService.decrementValue(decimalCounter2, step);
        simpleCounterService.getCurrentCounterCondition(decimalCounter2);
    }

    private static DecimalCounter buildDefaultDecimalCounter() {
        DecimalCounter decimalCounter = new DecimalCounter();
        System.out.println("Создаём счётчик по умолчанию\n" +
                "начальное значение счётчика = " + decimalCounter.getCounter() + "\n" +
                "левавя граница счётчика = " + decimalCounter.getLeftBound() + "\n" +
                "правая граница счётчика = " + decimalCounter.getRightBound() + "\n");
        return decimalCounter;
    }

    private static DecimalCounter buildUserDecimalCounter() {
        System.out.println("Создаём счётчик с параметрами пользователя");
        System.out.println("Введите начальное значение счётчика");
        int counterStartValue = enterInt();
        System.out.println("Введите левую границу счётчика");
        int leftBound = enterInt();
        System.out.println("Введите правую границу счётчика");
        int rightBound = enterInt();
        return new DecimalCounter(counterStartValue, leftBound, rightBound);
    }

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }
}
