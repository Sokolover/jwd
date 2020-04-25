package by.training.sokolov.task2.command;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.service.LibraryService;
import org.apache.log4j.Logger;

import java.util.Map;

public class GenreCountingCommand implements Command {

    private final static Logger LOGGER = Logger.getLogger(GenreCountingCommand.class.getName());

    private LibraryService service;

    public GenreCountingCommand() {

    }

    public GenreCountingCommand(LibraryService service) {
        this.service = service;
    }

    @Override
    public String execute(Map<String, String> map) {

        LOGGER.info("get page amount according to genre");
        String requestedGenre = map.get(LibraryAppConstants.QUERY_KEY_GENRE);
        String responseData = service.getPageAmountByGenre(service.getLibraryDao(), requestedGenre);

        LOGGER.info("build answer: page amount in publication genre - [" + requestedGenre + "]");
        String pageAmountResponse = buildHtmlAnswer(responseData);

        LOGGER.info("append page amount to answer");
        return pageAmountResponse;
    }

    private String buildHtmlAnswer(String response) {
        LOGGER.info("build answer according to request: how many pages of what genre was found");
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<h2>")
                .append(response)
                .append("</h2>");
        return new String(htmlBuilder);
    }

    @Override
    public String getName() {
        return LibraryAppConstants.QUERY_KEY_GENRE_COUNT_COMMAND;
    }
}
