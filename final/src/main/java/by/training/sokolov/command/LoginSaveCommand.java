package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.model.User;
import by.training.sokolov.service.GenericService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class LoginSaveCommand implements Command {

    private GenericService<User> userService;

    public LoginSaveCommand(GenericService<User> userService){
        this.userService=userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String login = request.getParameter("user.name");
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
        SecurityContext.getInstance().login(user, request.getSession().getId());

        return "redirect:?_command=" + CommandType.INDEX;
    }
}
