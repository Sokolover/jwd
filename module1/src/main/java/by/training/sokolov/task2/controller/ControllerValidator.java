package by.training.sokolov.task2.controller;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.command.FileReadingCommand;
import by.training.sokolov.task2.command.GenreCountingCommand;
import by.training.sokolov.task2.command.SortByNameDescendingCommand;
import by.training.sokolov.task2.command.TypeNotExistException;
import by.training.sokolov.task2.enums.Genre;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

class ControllerValidator {

    //    todo: валидация строки запроса - command=FIND_PATH&path=D://test.csv&genre=humor

    private final static Logger LOGGER = Logger.getLogger(ControllerValidator.class.getName());

    boolean isValidateUrl(Map<String, String> map) {

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

        if (!requestMap.containsKey(LibraryAppConstants.QUERY_KEY_PATH)) {
            String validationError = "can not find <" + LibraryAppConstants.QUERY_KEY_PATH + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.QUERY_KEY_GENRE)) {
            String validationError = "can not find <" + LibraryAppConstants.QUERY_KEY_GENRE + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.QUERY_KEY_GENRE_COUNT_COMMAND)) {
            String validationError = "can not find <" + LibraryAppConstants.QUERY_KEY_GENRE_COUNT_COMMAND + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND)) {
            String validationError = "can not find <" + LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
        if (!requestMap.containsKey(LibraryAppConstants.QUERY_KEY_SORT_BY)) {
            String validationError = "can not find <" + LibraryAppConstants.QUERY_KEY_SORT_BY + "> key in Query!";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
    }

//    long id;
//    String name;
//    PublicationType type;
//    Genre genre;
//    int pageAmount;

    private void validateUrlValues(Map<String, String> requestMap) throws Exception {

        String path = requestMap.get(LibraryAppConstants.QUERY_KEY_PATH);
        File file = new File(path);
        if (!file.exists()) {
            String validationError = "invalid file path in query. file on path \"" + path + "\" not exists";
            LOGGER.error(validationError);
            throw new FileNotFoundException(validationError);
        }

        String genre = requestMap.get(LibraryAppConstants.QUERY_KEY_GENRE);
        if (Genre.fromString(genre) == null) {
            String validationError = "invalid genre in query. error in method fromString() in class " + Genre.class.getName();
            LOGGER.error(validationError);
            throw new TypeNotExistException(validationError);
        }

        String commandName = requestMap.get(LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND);
        if (!commandName.equals(new FileReadingCommand().getName())) {
            String validationError = "invalid value in template.html: <" + LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }

        commandName = requestMap.get(LibraryAppConstants.QUERY_KEY_GENRE_COUNT_COMMAND);
        if (!commandName.equals(new GenreCountingCommand().getName())) {
            String validationError = "invalid value in template.html: <" + LibraryAppConstants.QUERY_KEY_GENRE_COUNT_COMMAND + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }

        commandName = requestMap.get(LibraryAppConstants.QUERY_KEY_SORT_BY);//fixme надо сделать чтобы приходило значение name по ключу SORT_BY и выбиралась сортировка по имени
        if (!commandName.equals(new SortByNameDescendingCommand().getName())) {
            String validationError = "invalid value in template.html: <" + LibraryAppConstants.QUERY_KEY_SORT_BY + "," + commandName + ">";
            LOGGER.error(validationError);
            throw new Exception(validationError);
        }
    }
}
