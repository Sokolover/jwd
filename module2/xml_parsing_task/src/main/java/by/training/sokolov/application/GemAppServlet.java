package by.training.sokolov.application;

import by.training.sokolov.commands.Command;
import by.training.sokolov.commands.CommandEnum;
import by.training.sokolov.commands.CommandFactory;
import by.training.sokolov.commands.CommandFactoryImpl;
import by.training.sokolov.dal.GemDaoImpl;
import by.training.sokolov.service.GemService;
import by.training.sokolov.service.SimpleGemService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.contants.GemAppConstants.QUERY_KEY_COMMAND;

@MultipartConfig
@WebServlet(name = "gem", urlPatterns = "/")
public class GemAppServlet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(GemAppServlet.class.getName());
    private static final long serialVersionUID = -4913979823837836732L;
    private CommandFactory commandFactory;
    private GemService gemService;

    @Override
    public void init() {
        GemDaoImpl gemDao = new GemDaoImpl();
        gemService = new SimpleGemService(gemDao);
        commandFactory = new CommandFactoryImpl(gemService);
        LOGGER.info("init server");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.info("doGet method handle");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/parsers.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.info("doPost method handle");

        String commandName = request.getParameter(QUERY_KEY_COMMAND);
        Command command = commandFactory.getCommand(commandName);
        String commandResult;

        switch (CommandEnum.fromString(commandName)) {

            case UPLOAD_COMMAND:
                commandResult = command.execute(request, response);
                request.setAttribute("gems", gemService.findAll());
                request.setAttribute("message", commandResult);
                doGet(request, response);
                break;
            case DOWNLOAD_COMMAND:
                command.execute(request, response);
                break;
            case DELETE_ALL_COMMAND:
                commandResult = command.execute(request, response);
                request.setAttribute("message", commandResult);
                doGet(request, response);
                break;
        }
    }
}