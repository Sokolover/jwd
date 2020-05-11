package by.training.sokolov.task2;

import by.training.sokolov.task2.server.LibraryHttpServer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        LibraryHttpServer libraryHttpServer = new LibraryHttpServer();
        libraryHttpServer.create();
    }
}
