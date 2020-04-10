package by.training.sokolov.oop.agregation_and_composition.task1.presentation;

import by.training.sokolov.oop.agregation_and_composition.task1.model.Sentence;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Text;
import by.training.sokolov.oop.agregation_and_composition.task1.model.Word;

import java.util.Scanner;

public class TextUI {

    private static int enterInt() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    public static boolean completeTextMenu() {
        System.out.println("Хотите дополнить текст?\n" +
                "1 - Да\n" +
                "2 - Нет\n");
        int choice = enterYesOrNoAnswer();
        return choice == 1;
    }

    private static int enterYesOrNoAnswer() {
        int choice;
        do {
            choice = enterInt();
        } while (choice < 0 || choice > 2);
        return choice;
    }

    public void showTask() {
        System.out.println("1. Создать объект класса Текст,\n" +
                " используя классы Предложение, Слово.\n" +
                " Методы:\n" +
                "  - дополнить текст,\n" +
                "  - вывести на консоль текст,\n" +
                "  - заголовок текста.\n");
    }

    public void outputText(Text text) {
        System.out.println("\n Ваш текст:");
        System.out.println(text.getHeader().getContent());
        System.out.println("______________");
        for (Sentence sentence : text.getSentences()) {
            for (Word word : sentence.getWords()) {
                System.out.print(word.getContent());
            }
        }
    }

    public void outputTextHeader(Text text) {
        System.out.println("\n Заголовок вашего текста");
        System.out.println(text.getHeader().getContent());
        System.out.println("______________");
        System.out.println();
    }

    public String enterHeader() {
        System.out.println("Введите заголовок текста");
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public int enterWordAmount() {
        System.out.println("Введите количество слов в предложении");
        return enterInt();
    }

    public String enterWordContent() {
        System.out.println("Введите слово:");
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public int enterSentenceAmount() {
        System.out.println("Сколько предложений будет в вашем тексте?");
        return enterInt();
    }


}
