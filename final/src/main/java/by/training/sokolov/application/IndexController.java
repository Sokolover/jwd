package by.training.sokolov.application;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.command.CommandFactoryImpl;
import by.training.sokolov.command.CommandUtil;
import by.training.sokolov.dao.CRUDDao;
import by.training.sokolov.dao.user.UserDaoImpl;
import by.training.sokolov.model.User;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.user.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/", name = "index")
public class IndexController extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 6154677369722697748L;

    private final static Logger LOGGER = Logger.getLogger(IndexController.class.getName());
    private CommandFactory commandFactory;
    private GenericService<User> userService;

    @Override
    public void init() {
        CRUDDao<User> userDao = new UserDaoImpl();
        userService = new UserServiceImpl(userDao);
        commandFactory= new CommandFactoryImpl(userService);
        LOGGER.info("init server");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String commandFromRequest = CommandUtil.getCommandFromRequest(req);
//        Optional<CommandType> of = CommandType.of(commandFromRequest);
//        Set<String> roles = (Set<String>) req.getSession().getAttribute("roles");


        Command command = commandFactory.getCommand(commandFromRequest);
        String viewName = command.apply(req, resp);
        if (viewName.startsWith("redirect:")) {
//            String redirect = viewName.replace("redirect:", "");
            String redirect = viewName.replace("redirect:", "final/");
            resp.sendRedirect(redirect);
        } else {
            req.setAttribute("viewName", viewName);
            req.getRequestDispatcher("/jsp/main_layout.jsp").forward(req, resp);
//            req.getRequestDispatcher("/jsp/nav_bar.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
