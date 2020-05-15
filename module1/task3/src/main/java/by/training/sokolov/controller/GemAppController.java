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

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        LOGGER.info("doGet method handle");
//        response.setContentType("text/html");
//
//        LOGGER.info("get template from resource for html answer");
//        InputStream resourceAsStream = this.getClass().getResourceAsStream(GemAppConstants.HTML_TEMPLATE_PATH_TASK3);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
//        String template = bufferedReader.lines().collect(Collectors.joining());
//
//        LOGGER.info("format publication response view according to template");
//        String view = MessageFormat.format(template, "<h3>\n" +
//                "    here you will see gem table\n" +
//                "</h3>");
//
//        response.setContentLength(view.getBytes().length);
//        response.getWriter().write(view);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        LOGGER.info("doPost method handle");
//        response.setContentType("text/html");
//
//        String commandName = request.getParameter(GemAppConstants.QUERY_KEY_COMMAND);
//        Command command = commandFactory.getCommand(commandName);
//        String message = command.execute(request, response);
//
//        LOGGER.info("get template from resource for html answer");
//        InputStream resourceAsStream = this.getClass().getResourceAsStream(GemAppConstants.HTML_TEMPLATE_PATH_TASK3);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
//        String template = bufferedReader.lines().collect(Collectors.joining());
//
//        LOGGER.info("format publication response view according to template");
//        String formatedResponse = MessageFormat.format(template, message);
//
//        response.getWriter().write(formatedResponse);
//    }


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
        doWork(request, response, command);
    }

    private void doWork(HttpServletRequest request, HttpServletResponse response, Command command) {
        try {
            String returnData = command.execute(request, response);
            request.setAttribute("gems", returnData);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/parsers.jsp");
            requestDispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.error("fatal error", e);
            request.setAttribute("returndata", "error on doWork() watch logs");
        }
    }

}