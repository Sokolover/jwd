package by.training.sokolov.oop.basic_of_oop.task5.presentation;

import by.training.sokolov.oop.basic_of_oop.task5.model.Composition;
import by.training.sokolov.oop.basic_of_oop.task5.model.Flower;
import by.training.sokolov.oop.basic_of_oop.task5.model.Wrapper;

import java.util.Scanner;

public class CompositionUI {

    public void showTask(){
        System.out.println("Цветочная композиция. \n" +
                "Реализовать приложение, позволяющее создавать цветочные композиции \n" +
                "(объект, представляющий собой цветочную композицию). \n" +
                "Составляющими цветочной композиции являются цветы и упаковка.\n");
    }

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public int enterFlowerAmount() {
        System.out.println("Введите количество цветов в вашем букете");
        return enterInt();
    }

    public int enterWrapperAmount() {
        System.out.println("Вы обернёте свои цветы в упаковки, количесвто которых будет...");
        return enterInt();
    }

    public void presentComposition(Composition composition) {
        System.out.println("Ваш букет состоит из");
        System.out.println("Цветов:");
        int i = 0;
        for (Flower flower : composition.getFlowerList()) {
            i++;
            System.out.println("\t" + i + ") " + flower.getSize() + " " + flower.getColor() + " " + flower.getName());
        }
        System.out.println("Слоёв упаковок:");
        i = 0;
        for (Wrapper wrapper : composition.getWrapperList()) {
            i++;
            System.out.println("\t" + i + ") " + wrapper.getColor() + " " + wrapper.getType());
        }
    }
}
