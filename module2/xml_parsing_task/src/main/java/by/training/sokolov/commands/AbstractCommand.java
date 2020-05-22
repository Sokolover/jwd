package by.training.sokolov.commands;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AbstractCommand.class.getName());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String message = "";
        try {
            message = executeWrapped(req, resp);
        } catch (Exception e) {
            LOGGER.error("Failed to execute command " + this.getClass().getName(), e);
        }
        return message;
    }

    protected abstract String executeWrapped(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String viewName) {

        try {
            req.setAttribute("viewName", viewName);
            req.getRequestDispatcher("/parsers.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to forward view", e);
        }
    }

    protected void redirect(HttpServletResponse resp, String redirect) {

        try {
            resp.sendRedirect(redirect);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to redirect", e);
        }
    }
}
