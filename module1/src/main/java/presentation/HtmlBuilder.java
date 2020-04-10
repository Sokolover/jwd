package presentation;

import enums.Genre;
import model.Publication;
import service.LibraryService;
import service.SimpleLibraryService;

import java.text.MessageFormat;
import java.util.List;

public class HtmlBuilder {

    public String buildDefaultHtmlAnswer() {
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
