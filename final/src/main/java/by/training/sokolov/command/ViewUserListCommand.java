package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ViewUserListCommand implements Command {

    private final UserService userService;

    public ViewUserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ConnectionException {
        request.setAttribute("userList", userService.findAll());
        return "user_list";
    }
}
