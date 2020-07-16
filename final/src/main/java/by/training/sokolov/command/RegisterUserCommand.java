package by.training.sokolov.command;

import by.training.sokolov.core.util.Md5EncryptingUtil;
import by.training.sokolov.db.ConnectionException;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.entity.user.service.UserService;
import by.training.sokolov.validation.BeanValidator;
import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.ValidationResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.application.constants.JspName.*;

public class RegisterUserCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterUserCommand.class.getName());

    private final UserService userService;
    private final BeanValidator validator;

    public RegisterUserCommand(UserService userService, BeanValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        /*
        TODO добвавить константы для параметров jsp!!!
            "user.password" и т.д.
            и для колонок  таблиц в dao
         */

        String name = request.getParameter("user.name");
        String email = request.getParameter("user.email");
        String password = request.getParameter("user.password");
        String phoneNumber = request.getParameter("user.phoneNumber");

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phoneNumber);

        ValidationResult validationResult = validator.validate(newUser);
        List<BrokenField> brokenFields = validationResult.getBrokenFields();

        if (brokenFields.isEmpty()) {

            String hashedPassword = Md5EncryptingUtil.encrypt(password);
            newUser.setPassword(hashedPassword);

            List<User> users = userService.findAll();
            /*
            todo сделать get by email и by name запрос с чуствительностью к регистру
             вместо этого поиска циклами. скорее всего сделатб регистрацию в userService
             */
            for (User user : users) {
                if (user.getName().equals(name)) {
                    request.setAttribute("error", "user with this name has been registered");
                    return USER_REGISTER_JSP;
                }
            }

            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    request.setAttribute("error", "user with this email has been registered");
                    return USER_REGISTER_JSP;
                }
            }

            /*
            todo валидация на адрес.
             */

            String address = request.getParameter("user.address");
            newUser.getUserAddress().setFullAddress(address);

            userService.register(newUser);

            request.setAttribute("message", "You have been registered successfully");
            return COMMAND_RESULT_MESSAGE_JSP;

        } else {

            StringBuilder message = createMessage(brokenFields);

            request.setAttribute("error", message);
        }

        return ERROR_MESSAGE_JSP;
    }

    private StringBuilder createMessage(List<BrokenField> brokenFields) {
        StringBuilder message = new StringBuilder();
        message.append("Invalid input in next field(s): ");

        for (int i = 0; i < brokenFields.size(); i++) {

            if (i == brokenFields.size() - 1) {
                message.append(brokenFields.get(i).getFieldName());
                break;
            }
            message.append(brokenFields.get(i).getFieldName());
            message.append(", ");
        }
        return message;
    }
}
