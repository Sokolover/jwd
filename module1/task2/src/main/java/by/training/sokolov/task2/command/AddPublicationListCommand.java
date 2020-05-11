package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.model.Publication;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddPublicationListCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(AddPublicationListCommand.class.getName());

    private LibraryService service;

    public AddPublicationListCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("add publication list to response");
        return addPublicationListToResponse();
    }

    private String addPublicationListToResponse() {

        LOGGER.info("build html answer: publication list with all publications");
        StringBuilder publicationResponse = new StringBuilder();

        publicationResponse
                .append("<h2>")
                .append("All publications:")
                .append("</h2>");

        publicationResponse.append("<ul>");
        List<Publication> publicationList = service.getLibraryDao().getPublicationList();

        for (Publication publication : publicationList) {
            publicationResponse
                    .append("<li>")
                    .append(publication.toString())
                    .append("</li>");
        }
        publicationResponse.append("</ul>");

        return new String(publicationResponse);
    }

    @Override
    public String getName() {
        return LibraryAppConstants.ADD_PUBLICATION_LIST_COMMAND;
    }
}
