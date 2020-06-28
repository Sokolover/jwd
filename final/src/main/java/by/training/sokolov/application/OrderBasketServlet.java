package by.training.sokolov.application;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/order_basket", name = "OrderBasketServlet")
public class OrderBasketServlet extends HttpServlet {

    private static final long serialVersionUID = -79412450294725257L;

    private final static Logger LOGGER = Logger.getLogger(OrderBasketServlet.class.getName());
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

        boolean flag = SecurityContext.getInstance().isUserLoggedIn(req);
        req.setAttribute("flag", flag);

        switch (viewName) {
            case "menu":
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case "logout":
                resp.sendRedirect(req.getContextPath());
                break;
            case "basket_add":
            case "basket":
            default:
                req.setAttribute("viewName", "basket");
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }


}
