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
import static by.training.sokolov.application.constants.ServletName.MENU_SERVLET;
import static by.training.sokolov.application.constants.ServletName.ORDER_BASKET_SERVLET;
import static by.training.sokolov.command.constants.CommandReturnValues.*;

@WebServlet(urlPatterns = "/order_basket", name = "OrderBasketServlet")
public class OrderBasketServlet extends HttpServlet {

    private static final long serialVersionUID = -79412450294725257L;

    private static final Logger LOGGER = Logger.getLogger(OrderBasketServlet.class.getName());
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
            case MENU_SERVLET:
            case ORDER_BASKET_SERVLET:
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
            case BASKET_ADD_ITEM:
            case DELETE_DISH_FROM_ORDER:
            default:
                String commandShowOrderList = String.valueOf(CommandType.ORDER_DISH_LIST_DISPLAY);
                command = commandFactory.getCommand(commandShowOrderList);
                command.apply(req, resp);
                req.setAttribute("viewName", ORDER_ITEM_LIST_JSP);
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
