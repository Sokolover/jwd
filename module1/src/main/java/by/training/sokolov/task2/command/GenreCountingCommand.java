package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.Map;

public class GenreCountingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(FileReadingCommand.class.getName());
    private final static String NAME = "count_by_genre";

    private LibraryService service;

    public GenreCountingCommand(){

    }

    public GenreCountingCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> map) {

        LOGGER.info("get page amount according to genre");
        String requestedGenre = map.get(LibraryAppConstants.URL_KEY_GENRE);
        String responseData = service.getPageAmountByGenre(service.getLibrary(), requestedGenre);

        LOGGER.info("build answer: page amount in publication genre - [" + requestedGenre + "]");
        String pageAmountResponse;
        if ("".equals(responseData)) {
            pageAmountResponse = buildDefaultHtmlAnswer();
        } else {
            pageAmountResponse = buildGoodHtmlAnswer(responseData);
        }
        LOGGER.info("append page amount to answer");

        return pageAmountResponse;
    }

    private String buildDefaultHtmlAnswer() {
        LOGGER.info("build default answer: no publications matching users request");
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Incorrect genre required. Please try enter genre again :-)")
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        return new String(htmlBuilder);
    }

    private String buildGoodHtmlAnswer(String response) {
        LOGGER.info("build answer according to request: how many pages of what genre was found");
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

    @Override
    public String getName() {
        return NAME;
    }
}
