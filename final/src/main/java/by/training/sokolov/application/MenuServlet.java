package by.training.sokolov.application;

import by.training.sokolov.core.security.SecurityContext;
import by.training.sokolov.command.*;
import by.training.sokolov.command.constants.CommandType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.application.constants.JspName.*;
import static by.training.sokolov.application.constants.ServletName.*;
import static by.training.sokolov.command.constants.CommandReturnValues.LOGOUT;

@WebServlet(urlPatterns = "/menu", name = "MenuServlet")
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 3075146766217683919L;

    private static final Logger LOGGER = Logger.getLogger(MenuServlet.class.getName());
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
            case ORDER_BASKET_SERVLET:
            case MENU_SERVLET:
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case LOGOUT:
                resp.sendRedirect(req.getContextPath());
                break;
            case ORDER_CREATED_JSP:
                req.setAttribute("viewName", viewName);
                req.setAttribute("category", INDEX_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            default:
                String commandShowMenu = String.valueOf(CommandType.VIEW_DISH_MENU);
                command = commandFactory.getCommand(commandShowMenu);
                command.apply(req, resp);
                req.setAttribute("viewName", DISH_LIST_JSP);
                req.setAttribute("category", DISH_CATEGORY_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
