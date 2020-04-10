package by.training.sokolov.oop.basic_of_oop.task2;

import java.util.Scanner;

public class PaymentUI {
    public int enterProductCounter() {
        System.out.println("Введите сколько продуктов вы хотите купить");
        return enterInt();
    }

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public int enterProductCost() {
        System.out.print("Введите стоимость\t");
        return enterInt();
    }

    public String enterProductName() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите имя продукта\t");
        return in.next();
    }

    public void printCheque(Payment payment) {
        System.out.println("\n" + payment.getName());
        System.out.println("_______________");
        for (int i = 0; i < payment.getProducts().length; i++) {
            System.out.println("Название товара:\t" + payment.getProducts()[i].getProductName());
            System.out.println("Стоимость:\t\t\t" + payment.getProducts()[i].getProductCost());
        }
        System.out.println("*****");
        System.out.println("Общая стоимость:\t" + payment.getOverallCost());
    }
}
