package by.training.sokolov.command;

import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;
import by.training.sokolov.util.Md5EncryptingUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.LOGIN_JSP;
import static by.training.sokolov.core.constants.ServletName.INDEX_SERVLET;

public class LoginSubmitCommand implements Command {

    private final UserService userService;

    public LoginSubmitCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String email = request.getParameter(USER_EMAIL_JSP_PARAM);
        String password = request.getParameter(USER_PASSWORD_JSP_PARAM);
        String hashedPassword = Md5EncryptingUtil.encrypt(password);
        User user = userService.login(email, hashedPassword);

        if (Objects.isNull(user)) {
            request.setAttribute(ERROR_JSP_ATTRIBUTE, "wrong email address or password");
            return LOGIN_JSP;
        }

        HttpSession httpSession = request.getSession();
        SecurityContext.getInstance().login(user, httpSession.getId());

        return INDEX_SERVLET;
    }
}
