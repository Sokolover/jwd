package server;

import com.sun.net.httpserver.HttpServer;
import controller.PublicationHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HTTPServer {

    public void create() throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        server.createContext("/library", new PublicationHandler());
        server.start();
    }
}

