package by.training.sokolov.command;

import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.application.constants.JspName.USER_REGISTER_JSP;
import static by.training.sokolov.application.constants.ServletName.DELIVERY_SERVLET;

public class RegisterUserCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterUserCommand.class.getName());

    private UserService userService;

    public RegisterUserCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        /*
        TODO добвавить константы для параметров jsp!!!
            "user.password" и т.д.
            и для колонок  таблиц в dao
         */

        List<User> users = userService.findAll();

        String name = request.getParameter("user.name");
        for (User user : users) {
            if (user.getName().equals(name)) {
                request.setAttribute("error", "user with this name has been registered");
                return USER_REGISTER_JSP;
            }
        }
        String password = request.getParameter("user.password");
        String email = request.getParameter("user.email");
        String phoneNumber = request.getParameter("user.phoneNumber");
        String address = request.getParameter("user.address");

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.getUserAddress().setFullAddress(address);

        userService.register(user);

        return DELIVERY_SERVLET;
    }
}
