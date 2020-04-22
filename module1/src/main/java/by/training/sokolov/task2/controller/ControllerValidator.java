package by.training.sokolov.task2.controller;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.command.FileReadingCommand;
import by.training.sokolov.task2.command.GenreCountingCommand;
import by.training.sokolov.task2.command.TypeNotExistException;
import by.training.sokolov.task2.enums.Genre;
import com.sun.net.httpserver.HttpExchange;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

class ControllerValidator {

    //    todo: валидация строки запроса - command=FIND_PATH&path=D://test.csv&genre=humor

    private final static Logger LOGGER = Logger.getLogger(ControllerValidator.class.getName());

    boolean isValidateUrl(Map<String, String> map){

        try {
            validateUlrKeys(map);
        } catch (Exception e) {
            LOGGER.error("invalid key in Query - " + e);
            return false;
        }

        try {
            validateUrlValues(map);
        } catch (Exception e) {
            LOGGER.error("invalid value in Query - " + e);
            return false;
        }

        return true;
    }

    private void validateUlrKeys(Map<String, String> requestMap) throws Exception {

        if (!requestMap.containsKey(LibraryAppConstants.URL_KEY_PATH)) {
            String validationError = "can not find PATH key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.URL_KEY_GENRE)) {
            String validationError = "can not find GENRE key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.URL_KEY_COMMAND_GENRE_COUNT)) {
            String validationError = "can not find COMMAND_GENRE_COUNT key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.URL_KEY_COMMAND_FILE_READ)) {
            String validationError = "can not find COMMAND_FILE_GET key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
    }

    private void validateUrlValues(Map<String, String> requestMap) throws Exception {

        String path = requestMap.get(LibraryAppConstants.URL_KEY_PATH);
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }

        String genre = requestMap.get(LibraryAppConstants.URL_KEY_GENRE);
        if (Genre.fromString(genre) == null) {
            throw new TypeNotExistException("error in method fromString() - " + Genre.class.getName());
        }

        String commandName = requestMap.get(LibraryAppConstants.URL_KEY_COMMAND_FILE_READ);
        if (!commandName.equals(new FileReadingCommand().getName())) {
            throw new Exception("invalid key-value in template.html: <" + LibraryAppConstants.URL_KEY_COMMAND_FILE_READ + "," + commandName + ">");
        }

        commandName = requestMap.get(LibraryAppConstants.URL_KEY_COMMAND_GENRE_COUNT);
        if (!commandName.equals(new GenreCountingCommand().getName())) {
            throw new Exception("invalid key-value in template.html: <" + LibraryAppConstants.URL_KEY_COMMAND_GENRE_COUNT + "," + commandName + ">");
        }
    }
}
