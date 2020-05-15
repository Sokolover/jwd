package by.training.sokolov.task3.controller;

import by.training.sokolov.task3.contants.GemAppConstants;
import by.training.sokolov.task3.controller.commands.Command;
import by.training.sokolov.task3.controller.commands.CommandFactory;
import by.training.sokolov.task3.controller.commands.SimpleCommandFactory;
import by.training.sokolov.task3.dal.GemDao;
import by.training.sokolov.task3.model.Gem;
import by.training.sokolov.task3.service.GemService;
import by.training.sokolov.task3.service.SimpleGemService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import static by.training.sokolov.task3.contants.GemAppConstants.HTML_TEMPLATE_PATH_TASK3;


@MultipartConfig
@WebServlet(name = "upload", urlPatterns = "/*")
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
        response.setContentType("text/html");

        LOGGER.info("get template from resource for html answer");
        InputStream resourceAsStream = this.getClass().getResourceAsStream(HTML_TEMPLATE_PATH_TASK3);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String template = bufferedReader.lines().collect(Collectors.joining());

        LOGGER.info("format publication response view according to template");
        String view = MessageFormat.format(template, "<h3>\n" +
                "    here you will see gem table\n" +
                "</h3>");

        response.setContentLength(view.getBytes().length);
        response.getWriter().write(view);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.info("doPost method handle");
        response.setContentType("text/html");

        String commandName = request.getParameter(GemAppConstants.QUERY_KEY_COMMAND);
        Command command = commandFactory.getCommand(commandName);
        String message = command.execute(request, response);

        LOGGER.info("get template from resource for html answer");
        InputStream resourceAsStream = this.getClass().getResourceAsStream(HTML_TEMPLATE_PATH_TASK3);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream));
        String template = bufferedReader.lines().collect(Collectors.joining());

        LOGGER.info("format publication response view according to template");
        String formatedResponse = MessageFormat.format(template, message);

        response.getWriter().write(formatedResponse);
    }




//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        LOGGER.info("doGet method handle");
//
//        Command command = commandFactory.getCommand("show");
//        doWork(request, response, command); // тут forward
//
////        response.setContentType("text/html");
////        response.setContentLength(GET_RESPONSE.getBytes().length);
////        response.getWriter().write(GET_RESPONSE);
////        try {
////            response.sendRedirect(request.getContextPath());
////        } catch (IOException e) {
////            LOGGER.info("server error post", e);
////        }

//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        LOGGER.info("doPost method handle");
//
//        String commandName = request.getParameter("command");//GemAppConstants.QUERY_KEY_COMMAND
//        Command command = commandFactory.getCommand(commandName);
//        doWork(request, response, command); // тут forward
//
////        try {
////            response.sendRedirect(request.getContextPath());
////        } catch (IOException e) {
////            LOGGER.info("server error post", e);
////        }

//    }
}