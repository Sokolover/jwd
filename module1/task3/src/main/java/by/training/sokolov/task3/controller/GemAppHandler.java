package by.training.sokolov.task3.controller;

import by.training.sokolov.task3.controller.commands.Command;
import by.training.sokolov.task3.controller.commands.CommandFactory;
import by.training.sokolov.task3.controller.validators.PathValidator;
import by.training.sokolov.task3.controller.validators.XMLValidator;
import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.service.GemService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.training.sokolov.task3.GemAppConstants.*;

public class GemAppHandler implements HttpHandler {

    private final static Logger LOGGER = Logger.getLogger(GemAppHandler.class.getName());
    private GemService service;
    private CommandFactory commandFactory;

    public GemAppHandler(CommandFactory commandFactory, GemService service) {
        this.commandFactory = commandFactory;
        this.service = service;
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

        StringBuilder response = new StringBuilder();

        if (!PathValidator.validateFilePath(map.get(QUERY_KEY_PATH))) {
            String message = "invalid file path from POST request";
            LOGGER.error(message);
            response.append(message);
            return;
        }

        if (!XMLValidator.checkXMLbyXSD(map.get(QUERY_KEY_PATH))) {
            String message = "error in checkXMLbyXSD() method";
            LOGGER.error(message);
            response.append(message);
            return;
        }

        executeFromQueryCommand(map, QUERY_KEY_FILE_PARSE_COMMAND);
        response.append(createGemHtmlTable());

        sendHandledResponse(exchange, response);
    }

    private String createGemHtmlTable() {

        StringBuilder response = new StringBuilder();

        response.append("<table border=\"1\">\n" +
                "    <caption>Gems table</caption>\n" +
                "    <tr>\n" +
                "        <th rowspan=\"2\" class=\"first\">id</th>\n" +
                "        <th rowspan=\"2\">Name</th>\n" +
                "        <th rowspan=\"2\">Preciousness</th>\n" +
                "        <th rowspan=\"2\">Origin</th>\n" +
                "        <th colspan=\"3\">Visual parameters</th>\n" +
                "        <th rowspan=\"2\">Value</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th class=\"first\">Color</th>\n" +
                "        <th class=\"first\">Transparency</th>\n" +
                "        <th class=\"first\">Number of faces</th>\n" +
                "    </tr>\n");

        List<Gem> gems = service.findAll();
        for (Gem gem : gems) {
            response.append("<tr><td>")
                    .append(gem.getId())
                    .append("</td><td>")
                    .append(gem.getName())
                    .append("</td><td>")
                    .append(gem.getPreciousness())
                    .append("</td><td>")
                    .append(gem.getOrigin())
                    .append("</td><td>")
                    .append(gem.getColor())
                    .append("</td><td>")
                    .append(gem.getTransparency())
                    .append("</td><td>")
                    .append(gem.getNumberOfFaces())
                    .append("</td><td>")
                    .append(gem.getValue())
                    .append("</td></tr>");
        }

        return new String(response);
    }


    private void sendHandledResponse(HttpExchange exchange, StringBuilder publicationResponse) throws IOException {

        LOGGER.info("get template from resource for html answer");
        InputStream resourceAsStream = this.getClass().getResourceAsStream(HTML_TEMPLATE_PATH_TASK3);
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

    private void executeFromQueryCommand(Map<String, String> map, String commandKey) {

        String commandName = map.get(commandKey);
        Command command = commandFactory.getCommand(commandName);
        String commandResult = command.execute(map);
        LOGGER.info("result of executing command <" + command.getName() + "> is: " + commandResult);
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

        return paramMap;
    }

}
