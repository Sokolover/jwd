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

import static by.training.sokolov.core.constants.JspName.LOGIN_JSP;
import static by.training.sokolov.core.constants.ServletName.LOGIN_SERVLET;

@WebServlet(urlPatterns = "/login", name = LOGIN_SERVLET)
public class LoginServletUtil extends HttpServlet {

    private static final long serialVersionUID = 1845229810562352696L;

    private static final Logger LOGGER = Logger.getLogger(LoginServletUtil.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FormServletUtil.formServletProcess(req, resp, commandFactory, LOGIN_JSP);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }
}
