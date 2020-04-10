package by.training.sokolov.oop.basic_of_oop.task1;

import java.util.List;

public class Directory {
    private String name;
    private List<File> fileList;

    public void outputInformation() {
        System.out.println("\nИмя:\t" + name);
        System.out.println("Файлы директории:");
        outputFileList();
    }

    private void outputFileList() {
        if (fileList.isEmpty()) {
            System.out.println("Нет файлов в директории");
            return;
        }
        for (File file : fileList) {
            System.out.println(" - " + file.getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
