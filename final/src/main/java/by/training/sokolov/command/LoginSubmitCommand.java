package by.training.sokolov.command;

import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.LOGIN_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static by.training.sokolov.core.constants.ServletName.INDEX_SERVLET;
import static by.training.sokolov.util.Md5EncryptingUtil.encrypt;
import static java.lang.String.format;

public class LoginSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(LoginSubmitCommand.class.getName());

    private final UserService userService;

    public LoginSubmitCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String email = request.getParameter(USER_EMAIL_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_EMAIL_JSP_PARAM, email));

        String password = request.getParameter(USER_PASSWORD_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_PASSWORD_JSP_PARAM, encrypt(password)));

        User user = userService.login(email, encrypt(password));

        if (Objects.isNull(user)) {

            String message = "Wrong email address or password";
            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

            return LOGIN_JSP;
        }

        String sessionId = request.getSession().getId();
        SecurityContext.getInstance().login(user, sessionId);

        return INDEX_SERVLET;
    }
}
