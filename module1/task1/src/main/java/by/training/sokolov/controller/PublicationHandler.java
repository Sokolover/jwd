package by.training.sokolov.task1.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import by.training.sokolov.task1.dal.LibraryDao;
import by.training.sokolov.task1.model.Library;
import org.apache.log4j.Logger;
import by.training.sokolov.task1.presentation.HtmlBuilder;
import by.training.sokolov.task1.service.LibraryService;
import by.training.sokolov.task1.service.SimpleLibraryService;

import java.io.*;
import java.net.URI;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class PublicationHandler implements HttpHandler {

    private final static Logger logger = Logger.getLogger(PublicationHandler.class.getName());

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            logger.info("handle GET request");
            requestParamValue = handleGetRequest(httpExchange);
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            logger.info("handle POST request");
            requestParamValue = handlePostRequest(httpExchange);
        }

        handleResponse(httpExchange, requestParamValue);
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        return "POST METHOD CAN NOT BE HANDLED";
    }

    private String handleGetRequest(HttpExchange httpExchange) {

        return httpExchange
                .getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private void handleResponse(HttpExchange exchange, String requestParamValue) throws IOException {

        logger.info("start handling response");
        HtmlBuilder htmlBuilder = new HtmlBuilder();

        logger.info("create library");
        Library library = new LibraryDao().buildLibrary();

        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        System.out.println("Received incoming request: " + requestMethod + " URI " + requestURI.toString());
        logger.info("Received incoming request: " + requestMethod + " URI " + requestURI.toString());

        String query = requestURI.getQuery();
        System.out.println("Queried: " + query);
        logger.info("Queried: " + query);

        logger.info("get template from resource for html answer");
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/templateTask2.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String template = bufferedReader.lines().collect(Collectors.joining());

        logger.info("add publication list to response");
        StringBuilder publicationResponse = htmlBuilder.addPublicationListToResponse(library);
        logger.info("create new library by.training.sokolov.task1.service");
        LibraryService simpleLibraryService = new SimpleLibraryService();
        logger.info("get page amount according to genre");
        String responseData = simpleLibraryService.getPageAmountByGenre(library, requestParamValue);
        logger.info("build answer about page amount");
        String pageAmountResponse;
        if ("".equals(responseData)) {
            pageAmountResponse = htmlBuilder.buildDefaultHtmlAnswer();
        } else {
            pageAmountResponse = htmlBuilder.buildGoodHtmlAnswer(responseData);
        }
        logger.info("append page amount to answer");
        publicationResponse.append(pageAmountResponse);
        logger.info("append requested publication list");
        StringBuilder requestedPublicationList = htmlBuilder.addRequestedPublicationList(library, requestParamValue);
        publicationResponse.append(requestedPublicationList);

        logger.info("format publication response view according to template");
        String view = MessageFormat.format(template, publicationResponse);

        logger.info("send response headers");
        exchange.sendResponseHeaders(200, view.length());
        OutputStream outputStream = exchange.getResponseBody();
        logger.info("send html response");
        outputStream.write(view.getBytes());

        outputStream.flush();
        outputStream.close();
    }
}
