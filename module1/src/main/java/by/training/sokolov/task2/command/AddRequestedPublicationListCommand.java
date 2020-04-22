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
    private final static String NAME = "add_requested_publication_list";

    private LibraryService service;

    public AddRequestedPublicationListCommand(LibraryService service) {
        this.service = service;
    }


    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("append requested publication list");
        String result = new String(addRequestedPublicationList(service.getLibrary(), requestGetMap.get(LibraryAppConstants.URL_KEY_GENRE)));
        return result;
    }

    private StringBuilder addRequestedPublicationList(Library library, String requestParamValue) {
        LOGGER.info("build html answer: publication list selected according to request");
        StringBuilder publicationResponse = new StringBuilder();
        LibraryService simpleLibraryService = new SimpleLibraryService();
        publicationResponse.append("<h2>");
        publicationResponse.append("List of publications by selected genre:");
        publicationResponse.append("</h2>");
        publicationResponse.append("<ul>");
        List<Publication> publicationList = simpleLibraryService.findAllPublications(library);
        for (Publication publication : publicationList) {
            try {
                if (Genre.fromString(requestParamValue) == publication.getGenre()) {
                    publicationResponse.append("<li>");
                    publicationResponse.append(publication.toString());
                    publicationResponse.append("</li>");
                }
            } catch (TypeNotExistException e) {
                LOGGER.error("addRequestedPublicationList() - publication genre don't exist!");
            }
        }
        publicationResponse.append("</ul>");
        return publicationResponse;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
