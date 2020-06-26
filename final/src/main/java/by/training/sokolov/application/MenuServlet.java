package by.training.sokolov.application;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.command.*;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/menu", name = "MenuServlet")
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 3075146766217683919L;

    private final static Logger LOGGER = Logger.getLogger(MenuServlet.class.getName());
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

        boolean flag = false;
        if(SecurityContext.getInstance().getCurrentUser(req.getSession().getId()) != null){
            flag = true;
        }
        req.setAttribute("flag", flag);

        switch (viewName) {
            case "login":
            case "user_register":
                resp.sendRedirect(req.getContextPath() + "/" + viewName);
                break;
            case "logout":
                resp.sendRedirect(req.getContextPath());
                break;
            case "menu":
            default:
                String commandShowMenu = String.valueOf(CommandType.DISH_MENU_SUBMIT);
                command = commandFactory.getCommand(commandShowMenu);
                command.apply(req, resp);
                req.setAttribute("viewName", "dish_list");
                req.setAttribute("category", "dish_category");
                req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
