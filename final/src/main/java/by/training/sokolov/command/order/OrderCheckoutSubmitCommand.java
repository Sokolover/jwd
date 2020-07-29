package by.training.sokolov.command.order;

import by.training.sokolov.command.Command;
import by.training.sokolov.context.SecurityContext;
import by.training.sokolov.database.connection.ConnectionException;
import by.training.sokolov.entity.order.model.UserOrder;
import by.training.sokolov.entity.order.service.UserOrderService;
import by.training.sokolov.entity.user.model.User;
import by.training.sokolov.validation.BeanValidator;
import by.training.sokolov.validation.BrokenField;
import by.training.sokolov.validation.ValidationResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static by.training.sokolov.core.constants.CommonAppConstants.*;
import static by.training.sokolov.core.constants.JspName.COMMAND_RESULT_MESSAGE_JSP;
import static by.training.sokolov.core.constants.JspName.ORDER_CHECKOUT_FORM_JSP;
import static by.training.sokolov.core.constants.LoggerConstants.*;
import static by.training.sokolov.entity.order.constants.OrderStatus.SUBMITTED;
import static by.training.sokolov.validation.CreateMessageUtil.createMessage;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class OrderCheckoutSubmitCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(OrderCheckoutSubmitCommand.class.getName());
    private static final String USERS_PARAM_TO_CURRENT_ORDER = "User's [%s] has been set to current order";
    private static final String CUSTOM_PARAM_TO_CURRENT_ORDER = "Custom [%s] has been set to current order";

    private final UserOrderService userOrderService;
    private final BeanValidator validator;

    public OrderCheckoutSubmitCommand(UserOrderService userOrderService, BeanValidator validator) {
        this.userOrderService = userOrderService;
        this.validator = validator;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ConnectionException {

        String sessionId = request.getSession().getId();
        UserOrder currentOrder = userOrderService.getCurrentUserOrder(sessionId);
        User user = SecurityContext.getInstance().getCurrentUser(sessionId);


        String customerName = request.getParameter(DEFAULT_USER_NAME_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DEFAULT_USER_NAME_JSP_PARAM, customerName));

        String customerPhoneNumber = request.getParameter(DEFAULT_USER_PHONE_NUMBER_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DEFAULT_USER_PHONE_NUMBER_JSP_PARAM, customerPhoneNumber));

        String customerUsersAddress = request.getParameter(DEFAULT_ORDER_ADDRESS_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, DEFAULT_ORDER_ADDRESS_JSP_PARAM, customerUsersAddress));


        if (nonNull(customerName)) {
            currentOrder.setCustomerName(user.getName());
            LOGGER.info(format(USERS_PARAM_TO_CURRENT_ORDER, "name"));
        } else {
            currentOrder.setCustomerName(request.getParameter(USER_NAME_JSP_PARAM));
            LOGGER.info(format(CUSTOM_PARAM_TO_CURRENT_ORDER, "name"));
        }

        if (nonNull(customerPhoneNumber)) {
            currentOrder.setCustomerPhoneNumber(user.getPhoneNumber());
            LOGGER.info(format(USERS_PARAM_TO_CURRENT_ORDER, "phone number"));
        } else {
            currentOrder.setCustomerPhoneNumber(request.getParameter(USER_PHONE_NUMBER_JSP_PARAM));
            LOGGER.info(format(CUSTOM_PARAM_TO_CURRENT_ORDER, "phone number"));
        }

        if (isNull(customerUsersAddress)) {
            setCustomAddress(request, currentOrder);
            LOGGER.info(format(CUSTOM_PARAM_TO_CURRENT_ORDER, "address"));
        } else {
            LOGGER.info(format(USERS_PARAM_TO_CURRENT_ORDER, "address"));
        }

        setTimeOfDelivery(request, currentOrder);

        currentOrder.setOrderStatus(SUBMITTED);
        LOGGER.info(format("Order status [%s] has been set to current order", SUBMITTED));

        ValidationResult validationResult = validator.validate(currentOrder);
        List<BrokenField> brokenFields = validationResult.getBrokenFields();

        if (brokenFields.isEmpty()) {

            userOrderService.update(currentOrder);

            String message = "Order accepted";
            request.setAttribute(MESSAGE_JSP_ATTRIBUTE, message);
            LOGGER.info(format(ATTRIBUTE_SET_TO_JSP_MESSAGE, message));
            return COMMAND_RESULT_MESSAGE_JSP;

        } else {

            String message = createMessage(brokenFields);

            request.setAttribute(ERROR_JSP_ATTRIBUTE, message);
            LOGGER.error(message);

            return ORDER_CHECKOUT_FORM_JSP;
        }
        
    }

    private void setTimeOfDelivery(HttpServletRequest request, UserOrder currentOrder) {

        /*
        todo протестировать работает или нет
         */
        String timeOfDeliveryMinutes = request.getParameter(ORDER_TIME_OF_DELIVERY_JSP_PARAM);
        LOGGER.info(format(PARAM_GOT_FROM_JSP_MESSAGE, ORDER_TIME_OF_DELIVERY_JSP_PARAM, timeOfDeliveryMinutes));

        LocalDateTime date = LocalDateTime.parse(timeOfDeliveryMinutes);
        currentOrder.setTimeOfDelivery(date);

        LOGGER.info(format(FROM_JSP_ATTR_SET_TO_CURRENT_ORDER_MESSAGE, ORDER_TIME_OF_DELIVERY_JSP_PARAM, currentOrder.getId()));
    }

    private void setCustomAddress(HttpServletRequest request, UserOrder currentOrder) {

        String locality = request.getParameter(ORDER_ADDRESS_LOCALITY_JSP_ATTRIBUTE);
        String street = request.getParameter(ORDER_ADDRESS_STREET_JSP_ATTRIBUTE);
        String buildingNumber = request.getParameter(ORDER_ADDRESS_BUILDING_NUMBER_JSP_ATTRIBUTE);
        String flatNumber = request.getParameter(ORDER_ADDRESS_FLAT_NUMBER_JSP_ATTRIBUTE);
        String porch = request.getParameter(ORDER_ADDRESS_PORCH_NUMBER_JSP_ATTRIBUTE);
        String floor = request.getParameter(ORDER_ADDRESS_FLOOR_NUMBER_JSP_ATTRIBUTE);

        StringBuilder address = new StringBuilder();
        address.append(locality)
                .append(",")
                .append(street)
                .append(",")
                .append(buildingNumber)
                .append(",")
                .append(flatNumber)
                .append(",")
                .append(porch)
                .append(",")
                .append(floor);

        currentOrder.getDeliveryAddress().setCustomDeliveryAddress(new String(address));

        LOGGER.info(format(FROM_JSP_ATTR_SET_TO_CURRENT_ORDER_MESSAGE, "Custom address", currentOrder.getId()));
    }
}
