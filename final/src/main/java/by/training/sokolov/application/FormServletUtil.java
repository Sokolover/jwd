package by.training.sokolov.application;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandUtil;
import by.training.sokolov.core.context.SecurityContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.command.constants.CommandReturnValues.DEFAULT_RESULT;
import static by.training.sokolov.core.constants.CommonAppConstants.VIEW_NAME_JSP_PARAM;
import static by.training.sokolov.core.constants.JspName.MAIN_LAYOUT_JSP;
import static by.training.sokolov.core.constants.ServletName.*;

class FormServletUtil {

    private FormServletUtil() {
    }

    static void formServletProcess(HttpServletRequest req, HttpServletResponse resp, CommandFactory commandFactory, String formJspName) throws IOException, ServletException {

        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);

//        boolean userLoggedIn = SecurityContext.getInstance().isUserLoggedIn(req);
//        req.setAttribute(USER_LOGGED_IN_JSP_PARAM, userLoggedIn);

        SecurityContext.getInstance().setSecurityAttributes(req);

        switch (viewName) {
            case LOGIN_SERVLET:
            case MENU_SERVLET:
            case USER_REGISTER_SERVLET:
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case INDEX_SERVLET:
                resp.sendRedirect(req.getContextPath());
                break;
            case DEFAULT_RESULT:
            default:
                req.setAttribute(VIEW_NAME_JSP_PARAM, formJspName);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
        }
    }
}
