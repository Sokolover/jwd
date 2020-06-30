package by.training.sokolov.command;

import by.training.sokolov.core.security.SecurityContext;
import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.user.model.User;
import by.training.sokolov.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static by.training.sokolov.application.constants.JspName.LOGIN_JSP;
import static by.training.sokolov.application.constants.ServletName.DELIVERY_SERVLET;

public class LoginSubmitCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String login = request.getParameter("user.name");
        UserService userService = BeanFactory.getUserService();
        List<User> users = userService.findAll();
        users = users
                .stream()
                .filter(u -> u.getName().equalsIgnoreCase(login))
                .collect(Collectors.toList());

        if (users.size() != 1) {
            request.setAttribute("error", "login invalid");
            return LOGIN_JSP;
        }

        User user = users.get(0);

        String reqPassword = request.getParameter("user.password");
        if (!user.getPassword().equals(reqPassword)) {
            request.setAttribute("error", "password invalid");
            return LOGIN_JSP;
        }

        HttpSession httpSession = request.getSession();
        SecurityContext.getInstance().login(user, httpSession.getId());

        return DELIVERY_SERVLET;
    }
}
