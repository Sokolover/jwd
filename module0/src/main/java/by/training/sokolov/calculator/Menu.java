package by.training.sokolov.calculator;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) throws IOException {

        boolean isRunning = true;
        while (isRunning) {
            showCommandsLevel1();

            int choice = enterInt();

            switch (choice) {
                case 1:
                    Calculator calculator = new Calculator();
                    calculator.calculate();
                    break;
                case 2:
                    isRunning = false;
                    break;
                default:
                    System.out.println("\nТакой команды нет, попробуйте ещё раз\n");
            }
        }

    }

    private static void showCommandsLevel1() {
        System.out.println("Калькулятор. Выберите действие");
        System.out.println("1 - Ввод в одну строку");
        System.out.println("2 - Выход");
    }

    public static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

}
