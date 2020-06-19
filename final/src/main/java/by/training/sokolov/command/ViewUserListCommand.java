//package by.training.sokolov.command;
//
//import com.bunis.um.dto.UserDto;
//import com.bunis.um.service.UserServiceFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//public class ViewUserListCommand implements Command {
//
//    @Override
//    public String process(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        List<UserDto> all = UserServiceFactory.getUserService().findAll();
//        request.setAttribute("userList", all);
//        return "user_list";
//    }
//}
