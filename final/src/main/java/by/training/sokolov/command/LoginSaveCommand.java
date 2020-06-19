package by.training.sokolov.command;

import by.training.sokolov.SecurityContext;
import by.training.sokolov.dto.user.UserDto;
import by.training.sokolov.service.GenericService;
import by.training.sokolov.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class LoginSaveCommand implements Command {

    private GenericService<UserDto> userService;

    public LoginSaveCommand(GenericService<UserDto> userService){
        this.userService=userService;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        String login = request.getParameter("user.name");
        List<UserDto> userDtos = userService.findAll();
        userDtos = userDtos
                .stream()
                .filter(u -> u.getName().equalsIgnoreCase(login))
                .collect(Collectors.toList());

        if (userDtos.size() != 1) {
            request.setAttribute("error", "login invalid");
            return "login";
        }

        UserDto userDto = userDtos.get(0);
        SecurityContext.getInstance().login(userDto, request.getSession().getId());

        return "redirect:?_command=" + CommandType.INDEX;
    }
}
