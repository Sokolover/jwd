package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.Map;

public class SortByNameCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(SortByNameCommand.class.getName());

    private LibraryService service;

    public SortByNameCommand() {

    }

    public SortByNameCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {

        String sortDirection = requestGetMap.get(LibraryAppConstants.QUERY_KEY_SORT_DIRECTION);
        if (LibraryAppConstants.DESCENDING_DIRECTION.equalsIgnoreCase(sortDirection)) {
            service.getLibraryDao().sortPublicationsByNameDescending();
        } else if (LibraryAppConstants.ASCENDING_DIRECTION.equalsIgnoreCase(sortDirection)) {
            service.getLibraryDao().sortPublicationsByNameAscending();
        }

        String message = "sort publication list " + sortDirection + " order in dao";
        LOGGER.info(message);

        return message;
    }

    @Override
    public String getName() {
        return LibraryAppConstants.SORT_COMMAND_NAME;
    }
}
