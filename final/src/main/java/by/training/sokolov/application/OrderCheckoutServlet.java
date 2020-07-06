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

import static by.training.sokolov.application.constants.JspName.*;
import static by.training.sokolov.application.constants.ServletName.*;
import static by.training.sokolov.command.constants.CommandReturnValues.LOGOUT;

@WebServlet(urlPatterns = "/order_checkout", name = "OrderCheckoutServlet")
public class OrderCheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = -8741776007076420891L;

    private static final Logger LOGGER = Logger.getLogger(OrderCheckoutServlet.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

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
            case ORDER_CHECKOUT_SERVLET:
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case LOGOUT:
                resp.sendRedirect(req.getContextPath());
                break;
            case ORDER_ACCEPTED_JSP:
                req.setAttribute("viewName", viewName);
                req.setAttribute("category", INDEX_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            case CHECKOUT_JSP:
            default:
                String commandShowMenu = String.valueOf(CommandType.CHECKOUT_ORDER_FORM_DISPLAY);
                command = commandFactory.getCommand(commandShowMenu);
                command.apply(req, resp);
                req.setAttribute("viewName", CHECKOUT_JSP);
                req.setAttribute("category", INDEX_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}