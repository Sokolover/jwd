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

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String, String> requestMap = new HashMap<>();
        if (LibraryAppConstants.HTTP_METHOD_GET.equals(exchange.getRequestMethod())) {
            LOGGER.info("parse GET request");
            requestMap = parseGetRequest(exchange);
        } else if (LibraryAppConstants.HTTP_METHOD_POST.equals(exchange.getRequestMethod())) {
            LOGGER.info("parse POST request");
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
        getInfoAboutRequest(exchange);

        if (isEmptyQuery(exchange, map)) {
            return;
        }

        ControllerValidator controllerValidator = new ControllerValidator();
        if (!controllerValidator.isValidateUrl(map)) {
            sendHandledResponse(exchange, new StringBuilder("Invalid file path or genre type. Try again"));
            return;
        }

        /*
        todo сделать сортировку
            1. показывается список критериев по которым сортируем
            2. вводится в поле критерий
            3. проверяется можно ли это сделать
            4. делается
         */

        executeFromQueryCommand(map, LibraryAppConstants.QUERY_KEY_FILE_READ_COMMAND);

        StringBuilder publicationResponse = new StringBuilder();

        String addPublicationListCommandResult = executeInMemoryCommand(map, LibraryAppConstants.ADD_PUBLICATION_LIST_COMMAND);
        publicationResponse.append(addPublicationListCommandResult);

        String genreCommandResult = executeFromQueryCommand(map, LibraryAppConstants.QUERY_KEY_GENRE_COUNT_COMMAND);
        publicationResponse.append(genreCommandResult);

        String requestedPublicationList = executeInMemoryCommand(map, LibraryAppConstants.ADD_REQUESTED_PUBLICATION_LIST_COMMAND);
        publicationResponse.append(requestedPublicationList);

        executeFromQueryCommand(map, LibraryAppConstants.QUERY_KEY_SORT_BY);
        addPublicationListCommandResult = executeInMemoryCommand(map, LibraryAppConstants.ADD_PUBLICATION_LIST_COMMAND);
        publicationResponse.append(addPublicationListCommandResult);

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

    private void getInfoAboutRequest(HttpExchange exchange) {
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

        //fixme пользоватьель должен вводить критерий по которому сортировать, а какой-то контроллер регулировать, какую сортироовку выбрать по какому критерию
        //fixme хотя возможно тут это и стоит оставить в роле контроллера
        if ("name".equals(paramMap.get(LibraryAppConstants.QUERY_KEY_SORT_BY))) {
            paramMap.put(LibraryAppConstants.QUERY_KEY_SORT_BY, LibraryAppConstants.SORT_PUBLICATION_LIST_BY_NAME_DESCENDING_COMMAND);
        }

        return paramMap;
    }
}