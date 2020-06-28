package by.training.sokolov.application;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.CommandUtil;
import by.training.sokolov.db.BasicConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*
    todo тут надо имя сделать как название класса сервлета,
        и команда переадресации на этот сервлет должна быть такого же имени,
        @
        понять что тут происходит, почему логаут появляется только если
        @WebServlet(urlPatterns = "/", name = "index")
        при name = "index"
 */
@WebServlet(urlPatterns = "/", name = "index")
public class IndexController extends HttpServlet {

    private static final long serialVersionUID = 6154677369722697748L;

    private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());
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

        /*
        todo заменить возвращаетмые значения команд константами
         */
        switch (viewName) {
            case "login":
            case "user_register":
            case "menu":
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case "create_order":
            case "logout":
                resp.sendRedirect(req.getContextPath());
                break;
            default:
                req.setAttribute("viewName", "index");
                req.setAttribute("category", "index");
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

    @Override
    public void destroy() {

        try {
            BasicConnectionPool.getInstance().shutdown();
            LOGGER.info("cp shutdown");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
