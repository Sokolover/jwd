package by.training.sokolov.controller;

import by.training.sokolov.controller.commands.Command;
import by.training.sokolov.controller.commands.CommandFactory;
import by.training.sokolov.controller.commands.SimpleCommandFactory;
import by.training.sokolov.dal.GemDao;
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
@WebServlet(urlPatterns = "/task3/*")
public class GemAppController extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(GemAppController.class.getName());
    private CommandFactory commandFactory;
    private GemService gemService;

    @Override
    public void init() {
        GemDao gemDao = new GemDao();
        gemService = new SimpleGemService(gemDao);
        commandFactory = new SimpleCommandFactory(gemService);
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
        command.execute(request, response);
        request.setAttribute("gems", gemService.findAll());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/parsers.jsp");
        requestDispatcher.forward(request, response);
    }

}