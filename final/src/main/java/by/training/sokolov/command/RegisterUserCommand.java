package by.training.sokolov.command;

import by.training.sokolov.model.User;
import by.training.sokolov.model.UserRole;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.role.UserRoleService;
import by.training.sokolov.service.role.UserRoleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegisterUserCommand implements Command {

    private Lock connectionLock = new ReentrantLock();
    private GenericService<User> userService;

    public RegisterUserCommand(GenericService<User> userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        List<User> users = userService.findAll();

        String name = request.getParameter("user.name");
        for (User user : users) {
            if (user.getName().equals(name)) {
                request.setAttribute("error", "user with this name has been registered");
                return "login";
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
        user.setActive(true);

        UserRole userRole = new UserRole("CLIENT");
        UserRoleService userRoleService = UserRoleServiceImpl.getInstance();
        Long roleId = userRoleService.getIdByRoleName(userRole);
        userRole.setId(roleId);
        user.setRoles(Collections.singletonList(userRole));

        user.getUserAddress().setFullAddress(address);

        userService.save(user);

        return "redirect:?_command=" + CommandType.INDEX;
    }


}
