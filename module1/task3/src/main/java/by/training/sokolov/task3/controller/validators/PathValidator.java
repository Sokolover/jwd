package by.training.sokolov.task3.controller.validators;

import java.io.File;

public class PathValidator {

    public static boolean validateFilePath(String path) {

        File file = new File(path);
        return file.exists();
    }
}
