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
import static by.training.sokolov.command.constants.CommandReturnValues.*;

@WebServlet(urlPatterns = "/order_basket", name = "OrderBasketServlet")
public class OrderBasketServlet extends HttpServlet {

    private static final long serialVersionUID = -79412450294725257L;

    private static final Logger LOGGER = Logger.getLogger(OrderBasketServlet.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

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
            case ORDER_CHECKOUT_SERVLET:
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case LOGOUT:
                resp.sendRedirect(req.getContextPath());
                break;
            case ORDER_CREATED_JSP:
            case DISH_FEEDBACK_WRITE_JSP:
                req.setAttribute("viewName", viewName);
//                req.setAttribute("category", INDEX_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
            case BASKET_ADD_ITEM:
            case DELETE_DISH_FROM_ORDER:
            default:
                String commandName = String.valueOf(CommandType.VIEW_ORDER_DISH_LIST);
                command = commandFactory.getCommand(commandName);
                String commandResult = command.apply(req, resp);
                req.setAttribute("viewName", commandResult);
//                req.setAttribute("category", DISH_CATEGORY_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
                break;
                /*
                todo сделать менее колходное создание заказа по дефолту,
                 если его нет, а он нужен!!!
                 */
//                if(commandResult.equals(CommandType.CREATE_ORDER.name())){
//                    String commandCreateOrder = String.valueOf(CommandType.CREATE_ORDER);
//                    command = commandFactory.getCommand(commandCreateOrder);
//                    command.apply(req, resp);
//                    commandName = String.valueOf(CommandType.VIEW_ORDER_DISH_LIST);
//                    command = commandFactory.getCommand(commandName);
//                    command.apply(req, resp);
//                }


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
