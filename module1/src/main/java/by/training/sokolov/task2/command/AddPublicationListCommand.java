package by.training.sokolov.task2.command;

import by.training.sokolov.task2.dal.Library;
import by.training.sokolov.task2.model.Publication;
import by.training.sokolov.task2.service.LibraryService;
import by.training.sokolov.task2.service.SimpleLibraryService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class AddPublicationListCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(AddPublicationListCommand.class.getName());
    private final static String NAME = "add_publication_list";


    private LibraryService service;

    public AddPublicationListCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> requestGetMap) {
        LOGGER.info("add publication list to response");
        String result = new String(addPublicationListToResponse(service.getLibrary()));
        return result;
    }

    private StringBuilder addPublicationListToResponse(Library library) {
        LOGGER.info("build html answer: publication list with all publications");
        StringBuilder publicationResponse = new StringBuilder();
        LibraryService simpleLibraryService = new SimpleLibraryService();
        publicationResponse.append("<ul>");
        List<Publication> publicationList = simpleLibraryService.findAllPublications(library);
        for (Publication publication : publicationList) {
            publicationResponse.append("<li>");
            publicationResponse.append(publication.toString());
            publicationResponse.append("</li>");
        }
        publicationResponse.append("</ul>");
        return publicationResponse;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
