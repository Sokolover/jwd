package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.Map;

public class SortByNameDescendingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(SortByNameDescendingCommand.class.getName());
    private final String name = LibraryAppConstants.SORT_PUBLICATION_LIST_BY_NAME_DESCENDING_COMMAND;
    private final String sortParam = "name";

    private LibraryService service;

    public SortByNameDescendingCommand(){

    }

    public SortByNameDescendingCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {

        String message = "sort publication list descending order in dao";
        LOGGER.info(message);
        service.getLibrary().sortPublicationsByNameDescending();

        return message;
    }

    public String getSortParam() {
        return sortParam;
    }

    @Override
    public String getName() {
        return name;
    }
}
