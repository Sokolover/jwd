package server;

import com.sun.net.httpserver.HttpServer;
import controller.PublicationHandler;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPServer {
    private final static Logger logger = Logger.getLogger(HTTPServer.class.getName());

    public void create() throws IOException {

        String hostName = "localhost";
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(hostName, port), 0);
        logger.info("created server on " + hostName + ":" + port);
        String contextName = "/library";
        server.createContext(contextName, new PublicationHandler());
        logger.info("created server context: " + contextName);
        logger.info("server started on " + server.getAddress());
        server.start();
    }
}

