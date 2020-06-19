package by.training.sokolov.command;

import by.training.sokolov.dao.UserRoleDao;
import by.training.sokolov.dao.UserRoleDaoImpl;
import by.training.sokolov.db.BasicConnectionPool;
import by.training.sokolov.dto.user.UserDto;
import by.training.sokolov.model.UserRole;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.UserRoleService;
import by.training.sokolov.service.UserRoleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegisterUserCommand implements Command {

    private Lock connectionLock = new ReentrantLock();
    private GenericService<UserDto> userService;

    public RegisterUserCommand(GenericService<UserDto> userService) {
        this.userService = userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        List<UserDto> userDtos = userService.findAll();

        String name = request.getParameter("user.name");
        for (UserDto userDto : userDtos) {
            if (userDto.getName().equals(name)) {
                request.setAttribute("error", "user with this name has been registered");
                return "login";
            }
        }

        String password = request.getParameter("user.password");
        String email = request.getParameter("user.email");
        String phoneNumber = request.getParameter("user.phoneNumber");
        String address = request.getParameter("user.address");

        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setPassword(password);
        userDto.setEmail(email);
        userDto.setPhoneNumber(phoneNumber);
        userDto.setActive(true);

        UserRole userRole = new UserRole("CLIENT");
        UserRoleService userRoleService = UserRoleServiceImpl.getInstance();
        Long roleId = userRoleService.getIdByRoleName(userRole);
        userRole.setId(roleId);
        userDto.setRoles(Collections.singletonList(userRole));

        userDto.getUserAddress().setFullAddress(address);

        userService.save(userDto);

        return "redirect:?_command=" + CommandType.INDEX;
    }


}
