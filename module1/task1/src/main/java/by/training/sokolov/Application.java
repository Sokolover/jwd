package by.training.sokolov;

import by.training.sokolov.server.HTTPServer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        HTTPServer httpServer = new HTTPServer();
        httpServer.create();
    }
}
