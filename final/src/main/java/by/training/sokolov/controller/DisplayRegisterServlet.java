package by.training.sokolov.controller;

import by.training.sokolov.command.Command;
import by.training.sokolov.command.CommandFactory;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.util.CommandUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.*;
import static by.training.sokolov.core.constants.ServletName.DISPLAY_REGISTER_SERVLET;

@WebServlet(urlPatterns = "/display_register", name = DISPLAY_REGISTER_SERVLET)
public class DisplayRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = -8104780406678115072L;

    private static final Logger LOGGER = Logger.getLogger(DisplayRegisterServlet.class.getName());
    private final CommandFactory commandFactory = ApplicationContext.getInstance().getBean(CommandFactory.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SecurityContext.getInstance().setSecurityAttributes(req);

        try {
            int success = Integer.parseInt(req.getParameter(QUERY_PARAM_SUCCESS));

            if (success == 1) {

                String message = req.getParameter(QUERY_PARAM_MESSAGE);
                req.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
                req.setAttribute(VIEW_NAME_JSP_PARAM, COMMAND_RESULT_MESSAGE_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
            } else {

                String message = req.getParameter(QUERY_PARAM_ERROR);
                req.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                req.setAttribute(VIEW_NAME_JSP_PARAM, REGISTER_JSP);
                req.getRequestDispatcher(MAIN_LAYOUT_JSP).forward(req, resp);
            }
        } catch (NumberFormatException e) {

            LOGGER.error(e.getMessage());

            String commandFromRequest = CommandUtil.getCommandFromRequest(req);
            Command command = commandFactory.getCommand(commandFromRequest);
            String viewName = command.apply(req, resp);

            resp.sendRedirect(String.format(REDIRECT_FORMAT, req.getContextPath(), viewName));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);
    }

}
