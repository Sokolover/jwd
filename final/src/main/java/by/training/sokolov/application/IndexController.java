package by.training.sokolov.application;

import by.training.sokolov.ApplicationModule;
import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.CommandUtil;
import by.training.sokolov.db.BasicConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*
    todo тут надо имя сделать как название класса сервлета,
        и команда переадресации на этот сервлет должна быть такого же имени,
        @
        понять что тут происходит, почему логаут появляется только если
        @WebServlet(urlPatterns = "/", name = "index")
        при name = "index"
        @
        настроить разные сервлеты для разных заданий!
 */
@WebServlet(urlPatterns = "/", name = "index")
public class IndexController extends HttpServlet {

    private static final long serialVersionUID = 6154677369722697748L;

    private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());
    private CommandFactory commandFactory;

    @Override
    public void init() {
        commandFactory = new CommandFactoryImpl();
        LOGGER.info("init server");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String test = req.getParameter(ApplicationModule.COMMAND_PARAM);
        String commandFromRequest = CommandUtil.getCommandFromRequest(req);

        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);

        /*
        fixme когда я залогинился и нажмаю Dish menu
              каким-то образом проникает незалогининая сессия в nav_bar,
              и nav_bar отображает список для незалогининого юзера
         */

        switch (viewName) {
            case "login":
            case "user_register":
            case "menu":
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case "logout":
                resp.sendRedirect(req.getContextPath());
                break;
            default:
                req.setAttribute("viewName", "index");
                req.setAttribute("category", "index");
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
                break;
        }

        //todo иф с логаутом и редирект а не приём команды а потом редирект


//        if (viewName.startsWith("redirect:")) {
//            String redirect = viewName.replace("redirect:", "delivery/");
////            String redirect = viewName.replace("redirect:", req.getContextPath());
//            resp.sendRedirect(redirect);
//        } else if (viewName.equals(DISH_MENU)) {
//            req.setAttribute("viewName", "dish_list");
//            req.setAttribute("category", "dish_category");
//            req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
//        } else {
//            req.setAttribute("viewName", viewName);
//            req.setAttribute("category", "index");
//            req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

    @Override
    public void destroy() {

        try {
            BasicConnectionPool.getInstance().shutdown();
            LOGGER.info("cp shutdown");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
