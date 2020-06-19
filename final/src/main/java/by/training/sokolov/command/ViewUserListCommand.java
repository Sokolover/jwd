package by.training.sokolov.command;

import by.training.sokolov.model.User;
import by.training.sokolov.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class ViewUserListCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        List<User> all = UserServiceImpl.getInstance().findAll();
        request.setAttribute("userList", all);
        return "user_list";
    }
}
