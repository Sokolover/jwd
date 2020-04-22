package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.Map;

public class SortByNameDescendingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(FileReadingCommand.class.getName());
    private final static String NAME = LibraryAppConstants.SORT_PUBLICATION_LIST_BY_NAME_DESCENDING;

    private LibraryService service;

    public SortByNameDescendingCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {
        service.getLibrary().sortPublicationsByNameDescending();
        LOGGER.info("sorted publication list descending order");
        return "sorted publication list descending order";
    }

    @Override
    public String getName() {
        return NAME;
    }
}
