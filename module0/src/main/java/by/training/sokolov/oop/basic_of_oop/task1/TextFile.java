package by.training.sokolov.oop.basic_of_oop.task1;

import java.util.Scanner;

public class TextFile extends File {
    private String content;

    @Override
    void create(Directory directory) {
        super.create(directory);
        name = name + ".txt";
        System.out.println("Имя созданного файла: " + name);
    }

    @Override
    void rename() {
        super.rename();
        name = name + ".txt";
        System.out.println("Новое имя файла: " + name);
    }

    @Override
    void displayContentOnConsole() {
        System.out.println("Содержимое файла:");
        if (content == null) {
            System.out.println("Файл пуст");
        } else {
            System.out.println(content);
        }
    }

    void addInformation() {
        Scanner in = new Scanner(System.in);
        System.out.println("Добавьте новую информацию: ");
        String newInfo = in.nextLine();
        if (content == null) {
            content = newInfo;
            System.out.println("Новое содержимое: " + content);
        } else {
            content = content + " " + newInfo;
            System.out.println("Новое содержимое: " + content);
        }
    }

    @Override
    void delete() {
        content = null;
        System.out.println("\nСодержимое файла " + name + " удалено");
    }
}
