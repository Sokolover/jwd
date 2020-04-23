package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.dal.Library;
import by.training.sokolov.task2.enums.Genre;
import by.training.sokolov.task2.model.Publication;
import by.training.sokolov.task2.service.LibraryService;
import by.training.sokolov.task2.service.SimpleLibraryService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddRequestedPublicationListCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(AddRequestedPublicationListCommand.class.getName());
    private final String name = LibraryAppConstants.ADD_REQUESTED_PUBLICATION_LIST_COMMAND;

    private LibraryService service;

    public AddRequestedPublicationListCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("append requested publication list");
        return addRequestedPublicationList(service.getLibrary(), requestGetMap.get(LibraryAppConstants.QUERY_KEY_GENRE));
    }

    private String addRequestedPublicationList(Library library, String requestParamValue) {

        LOGGER.info("build html answer: publication list selected according to request");
        StringBuilder publicationResponse = new StringBuilder();
        LibraryService simpleLibraryService = new SimpleLibraryService();

        publicationResponse
                .append("<h2>")
                .append("List of publications by selected genre:")
                .append("</h2>");

        publicationResponse.append("<ul>");
        List<Publication> publicationList = simpleLibraryService.findAllPublications(library);
        for (Publication publication : publicationList) {
            if (Genre.fromString(requestParamValue) == publication.getGenre()) {
                publicationResponse.append("<li>");
                publicationResponse.append(publication.toString());
                publicationResponse.append("</li>");
            }
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
