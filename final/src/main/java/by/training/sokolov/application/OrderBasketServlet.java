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

import static by.training.sokolov.command.constants.CommandReturnValues.*;
import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.*;
import static by.training.sokolov.core.constants.ServletName.*;

@WebServlet(urlPatterns = "/order_basket", name = ORDER_BASKET_SERVLET)
public class OrderBasketServlet extends HttpServlet {

    private static final long serialVersionUID = -79412450294725257L;

    private static final Logger LOGGER = Logger.getLogger(OrderBasketServlet.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);

        SecurityContext.getInstance().setSecurityAttributes(req);

        switch (viewName) {
            case MENU_SERVLET:
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
                req.setAttribute(VIEW_NAME_JSP_PARAM, viewName);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            case ORDER_ADD_ITEM_RESULT:
            case DELETE_DISH_FROM_ORDER_RESULT:
            default:
                String commandName = String.valueOf(CommandType.VIEW_ORDER_DISH_LIST);
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
