package by.training.sokolov.controller;

import by.training.sokolov.command.FileReadingCommand;
import by.training.sokolov.command.GenreCountingCommand;
import by.training.sokolov.command.SortByNameCommand;
import by.training.sokolov.command.TypeNotExistException;
import by.training.sokolov.enums.Genre;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import static by.training.sokolov.LibraryAppConstants.*;

public class ControllerValidator {

    private final static Logger LOGGER = Logger.getLogger(ControllerValidator.class.getName());

    public boolean isValidateUrl(Map<String, String> map) {

        try {
            validateUlrKeys(map);
        } catch (Exception e) {
            LOGGER.error("invalid Key in query - " + e);
            return false;
        }

        try {
            validateUrlValues(map);
        } catch (Exception e) {
            LOGGER.error("invalid Value in query - " + e);
            return false;
        }

        return true;
    }

    private void validateUlrKeys(Map<String, String> requestMap) throws Exception {

        if (!requestMap.containsKey(QUERY_KEY_PATH)) {
            String validationError = "can not find <" + QUERY_KEY_PATH + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(QUERY_KEY_GENRE)) {
            String validationError = "can not find <" + QUERY_KEY_GENRE + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(QUERY_KEY_GENRE_COUNT_COMMAND)) {
            String validationError = "can not find <" + QUERY_KEY_GENRE_COUNT_COMMAND + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(QUERY_KEY_FILE_READ_COMMAND)) {
            String validationError = "can not find <" + QUERY_KEY_FILE_READ_COMMAND + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(QUERY_KEY_SORT_BY)) {
            String validationError = "can not find <" + QUERY_KEY_SORT_BY + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
    }

    private void validateUrlValues(Map<String, String> requestMap) throws Exception {

        String path = requestMap.get(QUERY_KEY_PATH);
        File file = new File(path);
        if (!file.exists()) {
            String validationError = "invalid file path in query. file on path \"" + path + "\" not exists";
            LOGGER.error(validationError);
            throw new FileNotFoundException(validationError);
        }

        String genre = requestMap.get(QUERY_KEY_GENRE);
        if (Genre.fromString(genre) == null) {
            String validationError = "invalid genre in query. error in method fromString() in class " + Genre.class.getName();
            LOGGER.error(validationError);
            throw new TypeNotExistException(validationError);
        }

        String commandName = requestMap.get(QUERY_KEY_FILE_READ_COMMAND);
        if (!commandName.equals(new FileReadingCommand().getName())) {
            String validationError = "invalid value in templateTask2.html: <" + QUERY_KEY_FILE_READ_COMMAND + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }

        commandName = requestMap.get(QUERY_KEY_GENRE_COUNT_COMMAND);
        if (!commandName.equals(new GenreCountingCommand().getName())) {
            String validationError = "invalid value in templateTask2.html: <" + QUERY_KEY_GENRE_COUNT_COMMAND + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }

        commandName = requestMap.get(QUERY_KEY_SORT_BY);
        if (!commandName.equals(new SortByNameCommand().getName())) {
            String validationError = "invalid command name: <" + SORT_COMMAND_NAME + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }

        String directionName = requestMap.get(QUERY_KEY_SORT_DIRECTION).toUpperCase();
        if (!(directionName.equals(DESCENDING_DIRECTION) || directionName.equals(ASCENDING_DIRECTION))) {
            String validationError = "invalid command name: <" + SORT_COMMAND_NAME + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
    }
}
