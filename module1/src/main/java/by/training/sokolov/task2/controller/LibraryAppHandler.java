package by.training.sokolov.task2.controller;

import by.training.sokolov.task2.LibraryAppConstants;
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

public class LibraryAppHandler implements HttpHandler {

    private final static Logger LOGGER = Logger.getLogger(LibraryAppHandler.class.getName());
    private CommandFactory commandFactory;

    public LibraryAppHandler(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    //  http://localhost:8080/form?command=FIND_PATH&path=D://test.csv&genre=humor

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String, String> requestMap = null;
        if (LibraryAppConstants.HTTP_METHOD_GET.equals(exchange.getRequestMethod())) {
            LOGGER.info("handle GET request");
            requestMap = parseGetRequest(exchange);
        } else if (LibraryAppConstants.HTTP_METHOD_POST.equals(exchange.getRequestMethod())) {
            LOGGER.info("handle POST request");
            requestMap = parsePostRequest(exchange);
        }

        handleRequest(exchange, requestMap);
    }

    /*
        todo:
              написать ещё хотя бы один вариант сортировки
     */

    private void handleRequest(HttpExchange exchange, Map<String, String> map) throws IOException {

        LOGGER.info("start handling request");
        getInfoAboutRequestMethod(exchange);

        if (isEmptyQuery(exchange, map)) {
            return;
        }

        ControllerValidator controllerValidator = new ControllerValidator();
        if (!controllerValidator.isValidateUrl(map)) {
            sendHandledResponse(exchange, new StringBuilder("try again"));
            return;
        }

        //todo сделать html кнопку для сортировки

        executeQueryCommand(map, LibraryAppConstants.URL_KEY_COMMAND_FILE_READ);

        StringBuilder publicationResponse = new StringBuilder();

        executeInMemoryCommand(map, LibraryAppConstants.SORT_PUBLICATION_LIST_BY_NAME_DESCENDING);

        String addPublicationListCommandResult = executeInMemoryCommand(map, LibraryAppConstants.ADD_PUBLICATION_LIST_COMMAND);
        publicationResponse.append(addPublicationListCommandResult);

        String genreCommandResult = executeQueryCommand(map, LibraryAppConstants.URL_KEY_COMMAND_GENRE_COUNT);
        publicationResponse.append(genreCommandResult);

        String requestedPublicationList = executeInMemoryCommand(map, LibraryAppConstants.ADD_REQUESTED_PUBLICATION_LIST_COMMAND);
        publicationResponse.append(requestedPublicationList);

        sendHandledResponse(exchange, publicationResponse);

    }

    private boolean isEmptyQuery(HttpExchange exchange, Map<String, String> map) throws IOException {
        if ((map.isEmpty() && LibraryAppConstants.HTTP_METHOD_POST.equals(exchange.getRequestMethod())) ||
                LibraryAppConstants.HTTP_METHOD_GET.equals(exchange.getRequestMethod())) {
            sendHandledResponse(exchange, new StringBuilder());
            return true;
        }
        return false;
    }

    private void getInfoAboutRequestMethod(HttpExchange exchange) {
        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        LOGGER.info("Received incoming request: " + requestMethod + " URI " + requestURI.toString());

        String query = requestURI.getQuery();
        LOGGER.info("Queried: " + query);
    }

    private void sendHandledResponse(HttpExchange exchange, StringBuilder publicationResponse) throws IOException {

        LOGGER.info("get template from resource for html answer");
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/template.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String template = bufferedReader.lines().collect(Collectors.joining());

        LOGGER.info("format publication response view according to template");
        String view = MessageFormat.format(template, publicationResponse);

        LOGGER.info("send response headers");
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

    private String executeQueryCommand(Map<String, String> map, String commandKey) {

        String commandName = map.get(commandKey);
        Command command = commandFactory.getCommand(commandName);
        String commandResult = command.execute(map);
        LOGGER.info("result of executing command <" + command.getName() + "> is: " + commandResult);
        return commandResult;
    }

    private Map<String, String> parsePostRequest(HttpExchange httpExchange) {

        String keyValueSubstring = "";
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
        }

        return parseParamArray(keyValueSubstring);
    }

    private Map<String, String> parseGetRequest(HttpExchange httpExchange) {

        String keyValueSubstring = httpExchange.
                getRequestURI().
                toString();

        if ('?' != keyValueSubstring.charAt(keyValueSubstring.length() - 1)) {
            return new HashMap<>();
        }

        keyValueSubstring = httpExchange
                .getRequestURI()
                .getQuery();

        return parseParamArray(keyValueSubstring);
    }

    private Map<String, String> parseParamArray(String keyValueSubstring) {

        String[] paramStringArray = keyValueSubstring.split("&");
        Map<String, String> paramMap = new HashMap<>();

        for (String keyValue : paramStringArray) {
            if (keyValue.split("=").length == 2) {
                String key = keyValue.split("=")[0];
                String value = keyValue.split("=")[1];
                paramMap.put(key, value);
            } else {
                LOGGER.error("not enough info from request - can't parse request key=value");
                return new HashMap<>();
            }
        }
        return paramMap;
    }
}
