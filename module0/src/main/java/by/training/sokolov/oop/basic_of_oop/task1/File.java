package by.training.sokolov.oop.basic_of_oop.task1;

import java.util.Scanner;

public class File {

    String name;
    Directory directory;

    void create(Directory directory) {
        Scanner in = new Scanner(System.in);
        System.out.println("\nСоздаём файл");
        System.out.println("Введите имя файла: ");
        this.name = in.nextLine();
        this.directory = directory;
    }

    void rename() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nВведите новое имя файла: ");
        this.name = in.nextLine();
    }

    void displayContentOnConsole() {

    }

    void addInformation() {

    }

    void delete() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }
}
