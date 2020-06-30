package by.training.sokolov.command;

import by.training.sokolov.core.factory.BeanFactory;
import by.training.sokolov.role.model.UserRole;
import by.training.sokolov.role.service.UserRoleService;
import by.training.sokolov.user.model.User;
import by.training.sokolov.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static by.training.sokolov.application.constants.JspName.USER_REGISTER_JSP;
import static by.training.sokolov.application.constants.ServletName.DELIVERY_SERVLET;
import static by.training.sokolov.role.constants.RoleName.CLIENT;

public class RegisterUserCommand implements Command {

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        UserService userService = BeanFactory.getUserService();
        List<User> users = userService.findAll();

        String name = request.getParameter("user.name");
        for (User user : users) {
            if (user.getName().equals(name)) {
                request.setAttribute("error", "user with this name has been registered");
                return USER_REGISTER_JSP;
            }
        }
        /*
        TODO добвавить константы для параметров jsp!!!
            "user.password" и т.д.
         */
        String password = request.getParameter("user.password");
        String email = request.getParameter("user.email");
        String phoneNumber = request.getParameter("user.phoneNumber");
        String address = request.getParameter("user.address");

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setActive(true);

        UserRole userRole = new UserRole(CLIENT);

        UserRoleService userRoleService = BeanFactory.getUserRoleService();
        Long roleId = userRoleService.getIdByRoleName(userRole);
        userRole.setId(roleId);
        user.setRoles(Collections.singletonList(userRole));

        user.getUserAddress().setFullAddress(address);

        userService.save(user);

        return DELIVERY_SERVLET;
    }
}
