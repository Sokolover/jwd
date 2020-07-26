package by.training.sokolov;

import by.training.sokolov.server.LibraryHttpServer;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        LibraryHttpServer libraryHttpServer = new LibraryHttpServer();
        libraryHttpServer.create();
    }
}
