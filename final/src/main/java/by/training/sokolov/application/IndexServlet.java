package by.training.sokolov.application;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandUtil;
import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.core.context.SecurityContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.application.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.application.constants.JspName.MAIN_LAYOUT_JSP;
import static by.training.sokolov.application.constants.ServletName.*;
import static by.training.sokolov.command.constants.CommandReturnValues.LOGOUT_RESULT;

@WebServlet(urlPatterns = "/", name = INDEX_SERVLET)
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 6154677369722697748L;

    private static final Logger LOGGER = Logger.getLogger(IndexServlet.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);

        setSecurityAttributes(req);

        switch (viewName) {
            case LOGIN_SERVLET:
            case MENU_SERVLET:
            case USER_REGISTER_SERVLET:
            case ORDER_BASKET_SERVLET:
            case ORDER_CHECKOUT_SERVLET:
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case LOGOUT_RESULT:
                resp.sendRedirect(req.getContextPath());
                break;
            case COMMAND_RESULT_MESSAGE_JSP:
                req.setAttribute("viewName", viewName);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            default:
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
        }
    }

    private void setSecurityAttributes(HttpServletRequest req) {
        req.setAttribute("sessionId", req.getSession().getId());
        boolean userLoggedIn = SecurityContext.getInstance().isUserLoggedIn(req);
        req.setAttribute("userLoggedIn", userLoggedIn);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
