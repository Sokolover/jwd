package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.dal.Library;
import by.training.sokolov.task2.model.Publication;
import by.training.sokolov.task2.service.LibraryService;
import by.training.sokolov.task2.service.SimpleLibraryService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddPublicationListCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(AddPublicationListCommand.class.getName());
    private final String name = LibraryAppConstants.ADD_PUBLICATION_LIST_COMMAND;

    private LibraryService service;

    public AddPublicationListCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("add publication list to response");
        return addPublicationListToResponse(service.getLibrary());
    }

    private String addPublicationListToResponse(Library library) {

        LOGGER.info("build html answer: publication list with all publications");
        StringBuilder publicationResponse = new StringBuilder();
        LibraryService simpleLibraryService = new SimpleLibraryService();

        publicationResponse
                .append("<h2>")
                .append("All publications:")
                .append("</h2>");

        publicationResponse.append("<ul>");
        List<Publication> publicationList = simpleLibraryService.findAllPublications(library);
        for (Publication publication : publicationList) {
            publicationResponse.append("<li>");
            publicationResponse.append(publication.toString());
            publicationResponse.append("</li>");
            if (publication.toString() == null) {
                LOGGER.error("addRequestedPublicationList() - publication genre don't exist!");
            }
        }
        publicationResponse.append("</ul>");

        return new String(publicationResponse);
    }

    @Override
    public String getName() {
        return name;
    }
}
