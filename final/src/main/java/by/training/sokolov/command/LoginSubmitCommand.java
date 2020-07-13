package by.training.sokolov.command;

import by.training.sokolov.core.context.SecurityContext;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static by.training.sokolov.application.constants.JspName.LOGIN_JSP;
import static by.training.sokolov.application.constants.ServletName.INDEX_SERVLET;

public class LoginSubmitCommand implements Command {

    private final UserService userService;

    public LoginSubmitCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String name = request.getParameter("user.name");
        String password = request.getParameter("user.password");

        User user = userService.login(name, password);

        if (Objects.isNull(user)) {
            request.setAttribute("error", "wrong email address or password");
            return LOGIN_JSP;
        }

        HttpSession httpSession = request.getSession();
        SecurityContext.getInstance().login(user, httpSession.getId());

        return INDEX_SERVLET;
    }
}
