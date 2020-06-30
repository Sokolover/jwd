package by.training.sokolov.application;

import by.training.sokolov.core.security.SecurityContext;
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

import static by.training.sokolov.application.constants.JspName.*;
import static by.training.sokolov.application.constants.ServletName.*;
import static by.training.sokolov.command.constants.CommandReturnValues.LOGOUT;

/*
    fixme тут надо имя сделать как название класса сервлета,
        и команда переадресации на этот сервлет должна быть такого же имени,
        @
        понять что тут происходит, почему логаут появляется только если
        @WebServlet(urlPatterns = "/", name = "index")
        при name = "index"
        @
        сейчас (30.06) всё работает при названии IndexController
 */
@WebServlet(urlPatterns = "/", name = "IndexServlet")
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 6154677369722697748L;

    private static final Logger LOGGER = Logger.getLogger(IndexServlet.class.getName());
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

        boolean userLoggedIn = SecurityContext.getInstance().isUserLoggedIn(req);
        req.setAttribute("userLoggedIn", userLoggedIn);

        switch (viewName) {
            case LOGIN_SERVLET:
            case USER_REGISTER_SERVLET:
            case MENU_SERVLET:
            case ORDER_BASKET_SERVLET:
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case ORDER_CREATED_JSP:
                req.setAttribute("viewName", viewName);
                req.setAttribute("category", INDEX_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            case LOGOUT:
                resp.sendRedirect(req.getContextPath());
                break;
            default:
                req.setAttribute("viewName", INDEX_JSP);
                req.setAttribute("category", INDEX_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
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
