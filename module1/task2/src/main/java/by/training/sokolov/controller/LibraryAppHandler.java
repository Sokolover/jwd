package by.training.sokolov.task2.controller;

import by.training.sokolov.task2.command.Command;
import by.training.sokolov.task2.command.CommandFactory;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static by.training.sokolov.task2.LibraryAppConstants.*;

public class LibraryAppHandler implements HttpHandler {

    private final static Logger LOGGER = Logger.getLogger(LibraryAppHandler.class.getName());
    private CommandFactory commandFactory;

    public LibraryAppHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String, String> requestMap = new HashMap<>();
        if (HTTP_METHOD_GET.equals(exchange.getRequestMethod())) {
            LOGGER.info("parse GET request");
            requestMap = parseGetRequest(exchange);
        } else if (HTTP_METHOD_POST.equals(exchange.getRequestMethod())) {
            LOGGER.info("parse POST request");
            requestMap = parsePostRequest(exchange);
        }

        handleRequest(exchange, requestMap);
    }

    private void handleRequest(HttpExchange exchange, Map<String, String> map) throws IOException {

        LOGGER.info("start handling request");
        getInfoAboutRequest(exchange);

        if (isEmptyQuery(exchange, map)) {
            return;
        }

        ControllerValidator controllerValidator = new ControllerValidator();
        if (!controllerValidator.isValidateUrl(map)) {
            StringBuilder response = createAnswerForInvalidFieldInput();
            sendHandledResponse(exchange, response);
            return;
        }

        StringBuilder response = new StringBuilder();

        String infoAboutInvalidPublications = executeFromQueryCommand(map, QUERY_KEY_FILE_READ_COMMAND);
        response.append(infoAboutInvalidPublications);

        String addPublicationListCommandResult = executeInMemoryCommand(map, ADD_PUBLICATION_LIST_COMMAND);
        response.append(addPublicationListCommandResult);

        String genreCommandResult = executeFromQueryCommand(map, QUERY_KEY_GENRE_COUNT_COMMAND);
        response.append(genreCommandResult);

        String requestedPublicationList = executeInMemoryCommand(map, ADD_REQUESTED_PUBLICATION_LIST_COMMAND);
        response.append(requestedPublicationList);

        executeFromQueryCommand(map, QUERY_KEY_SORT_BY);
        addPublicationListCommandResult = executeInMemoryCommand(map, ADD_PUBLICATION_LIST_COMMAND);
        response.append(addPublicationListCommandResult);

        sendHandledResponse(exchange, response);

    }

    private StringBuilder createAnswerForInvalidFieldInput() {
        return new StringBuilder("<pre>\n" +
                "    Check one of this fields:\n" +
                "        - file path\n" +
                "        - genre type\n" +
                "        - sort by publication field\n" +
                "        - sort direction\n" +
                "    And try again\n" +
                "</pre>");
    }

    private boolean isEmptyQuery(HttpExchange exchange, Map<String, String> map) throws IOException {
        if ((map.isEmpty() && HTTP_METHOD_POST.equals(exchange.getRequestMethod())) ||
                HTTP_METHOD_GET.equals(exchange.getRequestMethod())) {
            sendHandledResponse(exchange, new StringBuilder());
            return true;
        }
        return false;
    }

    private void getInfoAboutRequest(HttpExchange exchange) {
        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        LOGGER.info("Received incoming request: " + requestMethod + " URI " + requestURI.toString());

        String query = requestURI.getQuery();
        LOGGER.info("Queried: " + query);
    }

    private void sendHandledResponse(HttpExchange exchange, StringBuilder publicationResponse) throws IOException {

        LOGGER.info("get template from resource for html answer");
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/templateTask2.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String template = bufferedReader.lines().collect(Collectors.joining());

        LOGGER.info("format publication response view according to template");
        String view = MessageFormat.format(template, publicationResponse);

        LOGGER.info("add response headers");
        exchange.sendResponseHeaders(200, view.getBytes(StandardCharsets.UTF_8).length);
        OutputStream outputStream = exchange.getResponseBody();
        LOGGER.info("send html response");
        outputStream.write(view.getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();
    }

    private String executeInMemoryCommand(Map<String, String> map, String commandName) {

        Command command = commandFactory.getCommand(commandName);
        String commandResult = command.execute(map);
        LOGGER.info("result of executing command <" + command.getName() + "> is: " + commandResult);
        return command.execute(map);
    }

    private String executeFromQueryCommand(Map<String, String> map, String commandKey) {

        String commandName = map.get(commandKey);
        Command command = commandFactory.getCommand(commandName);
        String commandResult = command.execute(map);
        LOGGER.info("result of executing command <" + command.getName() + "> is: " + commandResult);
        return commandResult;
    }

    private Map<String, String> parsePostRequest(HttpExchange httpExchange) {

        String keyValueSubstring;
        try {
            keyValueSubstring = URLDecoder.
                    decode(
                            new BufferedReader(
                                    new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8))
                                    .lines()
                                    .collect(Collectors.joining()),
                            StandardCharsets.UTF_8.name()
                    );
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
            return new HashMap<>();
        }

        return parseParamArray(keyValueSubstring);
    }

    private Map<String, String> parseGetRequest(HttpExchange httpExchange) {

        String requestURI = httpExchange.
                getRequestURI().
                toString();

        if ('?' != requestURI.charAt(requestURI.length() - 1)) {
            return new HashMap<>();
        }

        String query = httpExchange
                .getRequestURI()
                .getQuery();

        return parseParamArray(query);
    }

    private Map<String, String> parseParamArray(String query) {

        String[] paramStringArray = query.split("&");
        Map<String, String> paramMap = new HashMap<>();

        for (String keyValue : paramStringArray) {
            if (keyValue.split("=").length == 2) {
                String key = keyValue.split("=")[0];
                String value = keyValue.split("=")[1];
                paramMap.put(key, value);
            } else {
                LOGGER.error("not enough info in request. Can't parse query key=value string");
                return new HashMap<>();
            }
        }

        buildSortCommandName(paramMap);

        return paramMap;
    }

    private void buildSortCommandName(Map<String, String> paramMap) {

        String sortField = paramMap.get(QUERY_KEY_SORT_BY);

        String sortByCommandName = QUERY_KEY_SORT_BY +
                "_" + sortField.toUpperCase();

        paramMap.put(QUERY_KEY_SORT_BY, sortByCommandName);
    }
}