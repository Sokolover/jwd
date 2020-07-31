package by.training.sokolov.controller;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.util.CommandUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.core.constants.CommandReturnValues.LOGOUT_RESULT;
import static by.training.sokolov.core.constants.CommonAppConstants.VIEW_NAME_JSP_PARAM;
import static by.training.sokolov.core.constants.JspName.*;
import static by.training.sokolov.core.constants.ServletName.*;

final class FormServletUtil {

    private FormServletUtil() {
    }

    static void formServletProcess(HttpServletRequest req, HttpServletResponse resp, CommandFactory commandFactory, String formJspName) throws IOException, ServletException {

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
//            case INDEX_SERVLET:
                resp.sendRedirect(req.getContextPath());
                break;
            case DEFAULT_JSP:
                req.setAttribute(VIEW_NAME_JSP_PARAM, formJspName);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
//            default:
//                req.setAttribute(VIEW_NAME_JSP_PARAM, viewName);
//                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
//                break;
        }
    }
}
