package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.Map;

public class SortByNameCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(SortByNameCommand.class.getName());
    private final String name = LibraryAppConstants.SORT_COMMAND_NAME;

    private LibraryService service;

    public SortByNameCommand() {

    }

    public SortByNameCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {

        String message = "sort publication list descending order in dao";
        LOGGER.info(message);

        String sortDirection = requestGetMap.get(LibraryAppConstants.QUERY_KEY_SORT_DIRECTION);
        if (LibraryAppConstants.DESCENDING_DIRECTION.equalsIgnoreCase(sortDirection)) {
            service.getLibraryDao().sortPublicationsByNameDescending();
        } else if (LibraryAppConstants.ASCENDING_DIRECTION.equalsIgnoreCase(sortDirection)) {
            service.getLibraryDao().sortPublicationsByNameAscending();
        }

        return message;
    }

    @Override
    public String getName() {
        return name;
    }
}
