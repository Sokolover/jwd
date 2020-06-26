package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;
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

public class LoginSaveCommand implements Command {

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
            return "login";
        }

        User user = users.get(0);

        String reqPassword = request.getParameter("user.password");
        if (!user.getPassword().equals(reqPassword)) {
            request.setAttribute("error", "password invalid");
            return "login";
        }

        //fixme костыль для удержания сессии...
        //  не работает!
        HttpSession httpSession = request.getSession();
        SecurityContext.getInstance().login(user, httpSession.getId());

        return "delivery";
//        return "redirect:?_command=" + CommandType.INDEX;
    }
}
