package by.training.sokolov.server;

import by.training.sokolov.controller.PublicationHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPServer {
    private final static Logger logger = Logger.getLogger(HTTPServer.class.getName());

    public void create() throws IOException {

        String hostName = "localhost";
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(hostName, port), 0);
        logger.info("created by.training.sokolov.task1.server on " + hostName + ":" + port);
        String contextName = "/library";
        server.createContext(contextName, new PublicationHandler());
        logger.info("created by.training.sokolov.task1.server context: " + contextName);
        logger.info("by.training.sokolov.task1.server started on " + server.getAddress());
        server.start();
    }
}

