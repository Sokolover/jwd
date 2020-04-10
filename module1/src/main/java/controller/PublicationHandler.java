package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import presentation.HtmlBuilder;
import service.LibraryService;
import service.SimpleLibraryService;

import java.io.*;
import java.net.URI;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class PublicationHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            requestParamValue = handleGetRequest(httpExchange);
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
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

        HtmlBuilder htmlBuilder = new HtmlBuilder();

        String requestMethod = exchange.getRequestMethod();
        URI requestURI = exchange.getRequestURI();
        System.out.println("Received incoming request: " + requestMethod + " URI " + requestURI.toString());

        String query = requestURI.getQuery();
        System.out.println("Queried: " + query);

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/template.html");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String template = bufferedReader.lines().collect(Collectors.joining());

        StringBuilder publicationResponse = htmlBuilder.addPublicationListToResponse();
        LibraryService simpleLibraryService = new SimpleLibraryService();
        String responseData = simpleLibraryService.getPageAmountByGenre(requestParamValue);
        String pageAmountResponse;
        if ("".equals(responseData)) {
            pageAmountResponse = htmlBuilder.buildDefaultHtmlAnswer();
        } else {
            pageAmountResponse = htmlBuilder.buildGoodHtmlAnswer(responseData);
        }
        publicationResponse.append(pageAmountResponse);
        StringBuilder requestedPublicationList = htmlBuilder.addRequestedPublicationList(requestParamValue);
        publicationResponse.append(requestedPublicationList);

        String view = MessageFormat.format(template, publicationResponse);

        exchange.sendResponseHeaders(200, view.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(view.getBytes());

        outputStream.flush();
        outputStream.close();
    }
}
