package by.training.sokolov.calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static by.training.sokolov.calculator.Operators.*;

public class Calculator {

    private static void showCommandsLevel2() {
        System.out.println("\nДоступны команды:");
        System.out.println("Сумма                   (+)");
        System.out.println("Разность                (-)");
        System.out.println("Умножение               (*)");
        System.out.println("Деление                 (/)");
        System.out.println("Выход в начальное меню  (exit)");
        System.out.println("Ввод в формате          xxx.xxx + xxx.xxx");
        System.out.println("Пример:                 1 + 2.5\n");
    }

    void calculate() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            boolean isRunning = true;

            while (isRunning) {
                showCommandsLevel2();

                String line = reader.readLine();

                if (line.toLowerCase().equals("exit")) {
                    break;
                }

                line = line.replaceAll(" +", " ");
                line = line.trim();

                String[] input = line.split(" ");

                switch (input[1]) {
                    case "+":
                        add(Double.parseDouble(input[0]), Double.parseDouble(input[2]));
                        break;
                    case "-":
                        subtract(Double.parseDouble(input[0]), Double.parseDouble(input[2]));
                        break;
                    case "*":
                        multiply(Double.parseDouble(input[0]), Double.parseDouble(input[2]));
                        break;
                    case "/":
                        divide(Double.parseDouble(input[0]), Double.parseDouble(input[2]));
                        break;
                    case "exit":
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Попробуйте ещё раз");
                }
            }
        } catch (Exception e) {
            System.out.println("Неправильный формат ввода. Попробуйте ещё раз");
        }
    }
}
