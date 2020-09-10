package by.training.sokolov.controller;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.util.CommandUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.core.constants.CommandReturnValues.LOGOUT_RESULT;
import static by.training.sokolov.core.constants.CommonAppConstants.VIEW_NAME_JSP_PARAM;
import static by.training.sokolov.core.constants.JspName.MAIN_LAYOUT_JSP;
import static by.training.sokolov.core.constants.ServletName.*;

@MultipartConfig
@WebServlet(urlPatterns = "/", name = INDEX_SERVLET)
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 6154677369722697748L;
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);

        SecurityContext.getInstance().setSecurityAttributes(req);

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
            default:
                req.setAttribute(VIEW_NAME_JSP_PARAM, viewName);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
