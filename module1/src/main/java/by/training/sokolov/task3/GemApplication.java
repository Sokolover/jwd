package by.training.sokolov.task3;

import by.training.sokolov.task3.server.GemHttpServer;

import java.io.IOException;

public class GemApplication {
    public static void main(String[] args) throws IOException {
        GemHttpServer gemHttpServer = new GemHttpServer();
        gemHttpServer.create();
    }
}
