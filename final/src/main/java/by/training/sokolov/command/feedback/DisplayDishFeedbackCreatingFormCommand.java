package by.training.sokolov.command.feedback;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.ApplicationContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.util.JspUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static by.training.sokolov.core.constants.JspName.CREATE_DISH_FEEDBACK_FORM_JSP;

public class DisplayDishFeedbackCreatingFormCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DisplayDishFeedbackCreatingFormCommand.class.getName());

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {

        JspUtil jspUtil = ApplicationContext.getInstance().getBean(JspUtil.class);
        jspUtil.setDishAttributeByDishIdParam(request);
        LOGGER.info("Command have been processed");

        return CREATE_DISH_FEEDBACK_FORM_JSP;
    }

}
