package by.training.sokolov.task3.server;

import by.training.sokolov.task3.GemAppConstants;
import by.training.sokolov.task3.controller.GemAppHandler;
import by.training.sokolov.task3.controller.commands.Command;
import by.training.sokolov.task3.controller.commands.CommandFactory;
import by.training.sokolov.task3.controller.commands.FileParsingCommand;
import by.training.sokolov.task3.controller.commands.SimpleCommandFactory;
import by.training.sokolov.task3.service.GemService;
import by.training.sokolov.task3.service.SimpleGemService;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class GemHttpServer {

    private final static Logger LOGGER = Logger.getLogger(GemHttpServer.class.getName());

    public void create() throws IOException {

        InetSocketAddress inetSocketAddress = new InetSocketAddress(GemAppConstants.HOST_NAME, GemAppConstants.PORT_NUMBER);
        HttpServer server = HttpServer.create(inetSocketAddress, 0);
        LOGGER.info("create gem server on " + GemAppConstants.HOST_NAME + ":" + GemAppConstants.PORT_NUMBER);

        GemService gemService = new SimpleGemService();

        Map<String, Command> commandMap = new HashMap<>();

        FileParsingCommand fileParsingCommand = new FileParsingCommand(gemService);

        commandMap.put(fileParsingCommand.getName(), fileParsingCommand);

        CommandFactory commandFactory = new SimpleCommandFactory(commandMap);
        GemAppHandler gemAppHandler = new GemAppHandler(commandFactory, gemService);
        server.createContext(GemAppConstants.CONTEXT_NAME_TASK3, gemAppHandler);

        LOGGER.info("create server context: " + GemAppConstants.CONTEXT_NAME_TASK3);
        LOGGER.info("server started on " + server.getAddress());

        server.start();
    }
}

