package presentation;

import enums.Genre;
import model.Publication;
import org.apache.log4j.Logger;
import service.LibraryService;
import service.SimpleLibraryService;

import java.util.List;

public class HtmlBuilder {

    private final static Logger logger = Logger.getLogger(HtmlBuilder.class.getName());

    public String buildDefaultHtmlAnswer() {
        logger.info("build default answer: no publications matching users request");
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("incorrect value")
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        return new String(htmlBuilder);
    }

    public String buildGoodHtmlAnswer(String response) {
        logger.info("build answer according to request: how many pages of what genre was found");
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append(response)
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        return new String(htmlBuilder);
    }

    public StringBuilder addRequestedPublicationList(String requestParamValue) {
        logger.info("build html answer: publication list selected according to request");
        StringBuilder publicationResponse = new StringBuilder();
        LibraryService simpleLibraryService = new SimpleLibraryService();
        publicationResponse.append("<h2>");
        publicationResponse.append("List of publications by selected genre:");
        publicationResponse.append("</h2>");
        publicationResponse.append("<ul>");
        List<Publication> publicationList = simpleLibraryService.findAllPublications();
        for (Publication publication : publicationList) {
            if (Genre.fromString(requestParamValue) == publication.getGenre()) {
                publicationResponse.append("<li>");
                publicationResponse.append(publication.toString());
                publicationResponse.append("</li>");
            }
        }
        publicationResponse.append("</ul>");
        return publicationResponse;
    }

    public StringBuilder addPublicationListToResponse() {
        logger.info("build html answer: publication list with all publications");
        StringBuilder publicationResponse = new StringBuilder();
        LibraryService simpleLibraryService = new SimpleLibraryService();
        publicationResponse.append("<ul>");
        List<Publication> publicationList = simpleLibraryService.findAllPublications();
        for (Publication publication : publicationList) {
            publicationResponse.append("<li>");
            publicationResponse.append(publication.toString());
            publicationResponse.append("</li>");
        }
        publicationResponse.append("</ul>");
        return publicationResponse;
    }
}
