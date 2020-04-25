package by.training.sokolov.task2.server;

import by.training.sokolov.task2.LibraryAppConstants;
import by.training.sokolov.task2.command.*;
import by.training.sokolov.task2.controller.LibraryAppHandler;
import by.training.sokolov.task2.service.LibraryService;
import by.training.sokolov.task2.service.SimpleLibraryService;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class LibraryHttpServer {

    private final static Logger LOGGER = Logger.getLogger(LibraryHttpServer.class.getName());

    public void create() throws IOException {

        InetSocketAddress inetSocketAddress = new InetSocketAddress(LibraryAppConstants.HOST_NAME, LibraryAppConstants.PORT_NUMBER);
        HttpServer server = HttpServer.create(inetSocketAddress, 0);
        LOGGER.info("create task2 server on " + LibraryAppConstants.HOST_NAME + ":" + LibraryAppConstants.PORT_NUMBER);

        LibraryService libraryService = new SimpleLibraryService();

        Map<String, Command> commandMap = new HashMap<>();

        FileReadingCommand commandFileGet = new FileReadingCommand(libraryService);
        GenreCountingCommand commandGenre = new GenreCountingCommand(libraryService);
        AddPublicationListCommand commandAddList = new AddPublicationListCommand(libraryService);
        AddRequestedPublicationListCommand commandAddRequestedList = new AddRequestedPublicationListCommand(libraryService);
        SortByNameCommand sortByNameCommand = new SortByNameCommand(libraryService);

        commandMap.put(commandFileGet.getName(), commandFileGet);
        commandMap.put(commandGenre.getName(), commandGenre);
        commandMap.put(commandAddList.getName(), commandAddList);
        commandMap.put(commandAddRequestedList.getName(), commandAddRequestedList);
        commandMap.put(sortByNameCommand.getName(), sortByNameCommand);

        CommandFactory commandFactory = new SimpleCommandFactory(commandMap);
        LibraryAppHandler httpHandler = new LibraryAppHandler(commandFactory, libraryService);
        server.createContext(LibraryAppConstants.CONTEXT_NAME_TASK2, httpHandler);

        LOGGER.info("create server context: " + LibraryAppConstants.CONTEXT_NAME_TASK2);
        LOGGER.info("server started on " + server.getAddress());

        server.start();
    }
}

