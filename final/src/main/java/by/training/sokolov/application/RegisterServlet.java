package by.training.sokolov.application;

import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.core.context.ApplicationContext;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.application.constants.JspName.USER_REGISTER_JSP;
import static by.training.sokolov.application.constants.ServletName.USER_REGISTER_SERVLET;

@WebServlet(urlPatterns = "/user_register", name = USER_REGISTER_SERVLET)
public class RegisterServlet extends HttpServlet implements FormServlet {

    private static final long serialVersionUID = -8104780406678115072L;

    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        formServletProcess(req, resp, commandFactory, USER_REGISTER_JSP);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
