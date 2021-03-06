package by.training.sokolov.command;

import by.training.sokolov.LibraryAppConstants;
import by.training.sokolov.enums.Genre;
import by.training.sokolov.model.Publication;
import by.training.sokolov.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class AddRequestedPublicationListCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(AddRequestedPublicationListCommand.class.getName());

    private LibraryService service;

    public AddRequestedPublicationListCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("append requested publication list");
        return addRequestedPublicationList(requestGetMap.get(LibraryAppConstants.QUERY_KEY_GENRE));
    }

    private String addRequestedPublicationList(String requestParamValue) {

        LOGGER.info("build html answer: publication list selected according to request");
        StringBuilder publicationResponse = new StringBuilder();

        publicationResponse
                .append("<h2>")
                .append("List of publications by selected genre:")
                .append("</h2>");

        publicationResponse.append("<ul>");
        List<Publication> publicationList = service.getLibraryDao().getPublicationList();

        for (Publication publication : publicationList) {
            if (Genre.fromString(requestParamValue) == publication.getGenre()) {
                publicationResponse
                        .append("<li>")
                        .append(publication.toString())
                        .append("</li>");
            }
            if (isNull(Genre.fromString(requestParamValue))) {
                LOGGER.error("addRequestedPublicationList() - publication genre don't exist!");
            }
        }
        publicationResponse.append("</ul>");

        return new String(publicationResponse);
    }

    @Override
    public String getName() {
        return LibraryAppConstants.ADD_REQUESTED_PUBLICATION_LIST_COMMAND;
    }
}
