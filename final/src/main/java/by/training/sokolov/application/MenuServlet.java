package by.training.sokolov.application;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandUtil;
import by.training.sokolov.command.constants.CommandType;
import by.training.sokolov.core.context.ApplicationContext;
import by.training.sokolov.core.context.SecurityContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.command.constants.CommandReturnValues.LOGOUT_RESULT;
import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.*;
import static by.training.sokolov.core.constants.ServletName.*;

@WebServlet(urlPatterns = "/menu", name = MENU_SERVLET)
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 3075146766217683919L;

    private static final Logger LOGGER = Logger.getLogger(MenuServlet.class.getName());
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
            case COMMAND_RESULT_MESSAGE_JSP:
            case DISH_CREATE_FEEDBACK_JSP:
            case DISH_CREATE_FORM_JSP:
            case DISH_UPDATE_FORM_JSP:
                req.setAttribute(VIEW_NAME_JSP_PARAM, viewName);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            default:
                String commandName = String.valueOf(CommandType.VIEW_DISH_MENU);
                command = commandFactory.getCommand(commandName);
                String commandResult = command.apply(req, resp);
                req.setAttribute(VIEW_NAME_JSP_PARAM, commandResult);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
