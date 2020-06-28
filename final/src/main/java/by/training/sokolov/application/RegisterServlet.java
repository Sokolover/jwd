package by.training.sokolov.application;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/user_register", name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = -8104780406678115072L;

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

        boolean flag = SecurityContext.getInstance().isUserLoggedIn(req);
        req.setAttribute("flag", flag);

        switch (viewName) {
            case "login":
            case "menu":
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case "delivery":
                resp.sendRedirect(req.getContextPath());
                break;
            case "user_register":
            default:
                req.setAttribute("viewName", "user_register");
                req.setAttribute("category", "index");
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
                break;
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
