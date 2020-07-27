package by.training.sokolov.command.user;

import by.training.sokolov.command.Command;
import by.training.sokolov.database.connection.ConnectionException;
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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.*;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static by.training.sokolov.util.Md5EncryptingUtil.encrypt;
import static java.lang.String.format;

public class RegisterUserSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(RegisterUserSubmitCommand.class.getName());

    private final UserService userService;
    private final BeanValidator validator;

    public RegisterUserSubmitCommand(UserService userService, BeanValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        /*
        TODO добвавить константы для колонок таблиц в dao
         */

        String name = request.getParameter(USER_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_NAME_JSP_PARAM, name));

        String email = request.getParameter(USER_EMAIL_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_EMAIL_JSP_PARAM, email));

        String password = request.getParameter(USER_PASSWORD_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_PASSWORD_JSP_PARAM, password));

        String phoneNumber = request.getParameter(USER_PHONE_NUMBER_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_PHONE_NUMBER_JSP_PARAM, phoneNumber));

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhoneNumber(phoneNumber);

        ValidationResult validationResult = validator.validate(newUser);
        List<BrokenField> brokenFields = validationResult.getBrokenFields();

        if (brokenFields.isEmpty()) {

            String encryptedPassword;

            try {

                encryptedPassword = encrypt(password);
            } catch (NoSuchAlgorithmException e) {

                request.setAttribute(ERROR_JSP_ATTRIBUTE, e.getMessage());
                LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, e.getMessage()));
                LOGGER.error(e.getMessage());

                return REGISTER_JSP;
            }

            newUser.setPassword(encryptedPassword);

            List<User> users = userService.findAll();
            /*
            todo сделать get by email и by name запрос с чуствительностью к регистру
             вместо этого поиска циклами. скорее всего сделатб регистрацию в userService
             */
            for (User user : users) {

                if (user.getName().equalsIgnoreCase(name)) {

                    String message = "User with this name has been registered";
                    request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                    LOGGER.error(message);

                    return REGISTER_JSP;
                }

                if (user.getEmail().equalsIgnoreCase(email)) {

                    String message = "User with this email has been registered";
                    request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                    LOGGER.error(message);

                    return REGISTER_JSP;
                }
            }

            /*
            todo валидация на адрес.
             */

            String address = request.getParameter(USER_ADDRESS_JSP_PARAM);
            LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_ADDRESS_JSP_PARAM, address));
            newUser.getUserAddress().setFullAddress(address);

            userService.register(newUser);

            String message = "You have been registered successfully";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));

            return COMMAND_RESULT_MESSAGE_JSP;

        } else {

            StringBuilder message = createMessage(brokenFields);

            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.error(message);

            return REGISTER_JSP;
        }

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
