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
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.REGISTER_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.ATTRIBUTE_SET_TO_JSP_MESSAGE;
import static by.training.sokolov.core.constants.LoggerConstants.PARAM_GOT_FROM_JSP_MESSAGE;
import static by.training.sokolov.core.constants.ServletName.DISPLAY_REGISTER_SERVLET;
import static by.training.sokolov.util.Md5EncryptingUtil.encrypt;
import static by.training.sokolov.validation.CreateMessageUtil.createMessage;
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

        String name = request.getParameter(USER_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_NAME_JSP_PARAM, name));

        String email = request.getParameter(USER_EMAIL_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_EMAIL_JSP_PARAM, email));

        String password = request.getParameter(USER_PASSWORD_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_PASSWORD_JSP_PARAM, "NOT SHOWN"));

        String passwordConfirm = request.getParameter(USER_PASSWORD_CONFIRM_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, USER_PASSWORD_CONFIRM_JSP_PARAM, "NOT SHOWN"));

        if(passwordConfirm.trim().isEmpty() || !password.equals(passwordConfirm)){
            String message = "You haven't confirmed password correctly";
            int status = NOT_SUCCESSFUL;
            response.sendRedirect(format(REGISTER_REDIRECT_WITH_PARAMS_FORMAT, request.getContextPath(), DISPLAY_REGISTER_SERVLET, QUERY_PARAM_SUCCESS, status, QUERY_PARAM_ERROR, message));
            return REGISTER_JSP;
        }

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

                int status = NOT_SUCCESSFUL;
                response.sendRedirect(format(REGISTER_REDIRECT_WITH_PARAMS_FORMAT, request.getContextPath(), DISPLAY_REGISTER_SERVLET, QUERY_PARAM_SUCCESS, status, QUERY_PARAM_ERROR, e.getMessage()));

                return REGISTER_JSP;
            }

            newUser.setPassword(encryptedPassword);

            List<User> users = userService.findAll();
            /*
            todo МОЖЕТ БЫТЬ сделать get by email и by name запрос с чуствительностью к регистру
             вместо этого поиска циклами.
             */
            for (User user : users) {

                if (user.getName().equalsIgnoreCase(name)) {

                    String message = "User with this name has been registered";
                    request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                    LOGGER.error(message);

                    int status = NOT_SUCCESSFUL;
                    response.sendRedirect(format(REGISTER_REDIRECT_WITH_PARAMS_FORMAT, request.getContextPath(), DISPLAY_REGISTER_SERVLET, QUERY_PARAM_SUCCESS, status, QUERY_PARAM_ERROR, message));

                    return REGISTER_JSP;
                }

                if (user.getEmail().equalsIgnoreCase(email)) {

                    String message = "User with this email has been registered";
                    request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
                    LOGGER.error(message);

                    int status = NOT_SUCCESSFUL;
                    response.sendRedirect(format(REGISTER_REDIRECT_WITH_PARAMS_FORMAT, request.getContextPath(), DISPLAY_REGISTER_SERVLET, QUERY_PARAM_SUCCESS, status, QUERY_PARAM_ERROR, message));

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
            LOGGER.info(message);

            int status = SUCCESSFULLY;
            response.sendRedirect(format(REGISTER_REDIRECT_WITH_PARAMS_FORMAT, request.getContextPath(), DISPLAY_REGISTER_SERVLET, QUERY_PARAM_SUCCESS, status, QUERY_PARAM_MESSAGE, message));

            return COMMAND_RESULT_MESSAGE_JSP;

        } else {

            String message = createMessage(brokenFields);

            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.error(message);

            int status = NOT_SUCCESSFUL;
            response.sendRedirect(format(REGISTER_REDIRECT_WITH_PARAMS_FORMAT, request.getContextPath(), DISPLAY_REGISTER_SERVLET, QUERY_PARAM_SUCCESS, status, QUERY_PARAM_ERROR, message));

            return REGISTER_JSP;
        }

    }

}
