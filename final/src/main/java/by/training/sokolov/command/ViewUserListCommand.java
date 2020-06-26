package by.training.sokolov.command;

import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.user.model.User;
import by.training.sokolov.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class ViewUserListCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        UserService userService = BeanFactory.getUserService();
        List<User> allUsers = userService.findAll();
        request.setAttribute("userList", allUsers);
        return "user_list";
    }
}
