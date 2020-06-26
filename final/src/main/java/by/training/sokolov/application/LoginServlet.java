package by.training.sokolov.application;

import by.training.sokolov.command.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login", name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1845229810562352696L;

    private final static Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private CommandFactory commandFactory;

    @Override
    public void init() {
        commandFactory = new CommandFactoryImpl();
        LOGGER.info("init server");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);

        switch (viewName) {
            case "menu":
            case "user_register":
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case "delivery":
                resp.sendRedirect(req.getContextPath());
                break;
            case "login":
            default:
                req.setAttribute("viewName", "login");
                req.setAttribute("category", "index");
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
                break;
        }


//        //todo написать другие команды, более прозоичные и понятные
//        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
//        Command command = commandFactory.getCommand(commandFromRequest);
//        String viewName = command.apply(req, resp);
//        if (viewName.startsWith("redirect:")) {
//            resp.sendRedirect(req.getContextPath());
//        } else {
//            req.setAttribute("viewName", "login");
//            req.setAttribute("category", "index");
//            req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
