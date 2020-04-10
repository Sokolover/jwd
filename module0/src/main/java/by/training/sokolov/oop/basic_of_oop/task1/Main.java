package by.training.sokolov.oop.basic_of_oop.task1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Создать объект класса Текстовый файл, \n" +
                "используя классы Файл, Директория. \n" +
                "Методы: \n" +
                " - создать, \n" +
                " - переименовать, \n" +
                " - вывести на консоль содержимое, \n" +
                " - дополнить, \n" +
                " - удалить.\n");

        ArrayList<File> textFileList = new ArrayList<File>();
        TextFile textFile = new TextFile();
        Directory directory = new Directory();

        directory.setName("workDir/");
        directory.setFileList(textFileList);
        directory.outputInformation();

        textFile.create(directory);
        textFile.displayContentOnConsole();
        textFile.rename();
        textFile.addInformation();
        textFile.addInformation();

        textFileList.add(textFile);
        directory.setFileList(textFileList);
        directory.outputInformation();

        textFile.delete();
        textFile.displayContentOnConsole();
    }
}
