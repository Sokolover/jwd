package by.training.sokolov.core.constants;

public final class CommonAppConstants {

    public static final String USER_LOGGED_IN_JSP_PARAM = "userLoggedIn";
    public static final String VIEW_NAME_JSP_PARAM = "viewName";
    public static final String SESSION_ID_JSP_PARAM = "sessionId";
    public static final String SECURITY_CONTEXT_JSP_PARAM = "securityContext";

    public static final String FEEDBACK_RATING_JSP_PARAM = "feedback.rating";
    public static final String FEEDBACK_TEXT_JSP_PARAM = "feedback.text";

    public static final String DISH_ID_JSP_PARAM = "dish.id";

    public static final String ORDER_ITEM_ID_JSP_PARAM = "orderItem.id";
    public static final String DEFAULT_ORDER_ADDRESS_JSP_PARAM = "default.order.address";
    public static final String ORDER_ITEM_LIST_JSP_ATTRIBUTE = "itemList";
    public static final String ORDER_TIME_OF_DELIVERY_JSP_ATTRIBUTE = "order.timeOfDelivery";

    public static final String ORDER_DISH_AMOUNT_JSP_PARAM = "order.dish.amount";
    public static final String ORDER_ADDRESS_LOCALITY_JSP_ATTRIBUTE = "order.address.locality";
    public static final String ORDER_ADDRESS_STREET_JSP_ATTRIBUTE = "order.address.street";
    public static final String ORDER_ADDRESS_BUILDING_NUMBER_JSP_ATTRIBUTE = "order.address.buildingNumber";
    public static final String ORDER_ADDRESS_FLAT_NUMBER_JSP_ATTRIBUTE = "order.address.flatNumber";
    public static final String ORDER_ADDRESS_PORCH_NUMBER_JSP_ATTRIBUTE = "order.address.porch";
    public static final String ORDER_ADDRESS_FLOOR_NUMBER_JSP_ATTRIBUTE = "order.address.floor";

    public static final String TOTAL_ORDER_COST_JSP_ATTRIBUTE = "totalCost";
    public static final String TIME_LIST_JSP_ATTRIBUTE = "timeList";

    public static final String DEFAULT_USER_NAME_JSP_PARAM = "default.user.name";
    public static final String USER_NAME_JSP_PARAM = "user.name";
    public static final String USER_EMAIL_JSP_PARAM = "user.email";
    public static final String USER_PASSWORD_JSP_PARAM = "user.password";
    public static final String USER_ADDRESS_JSP_PARAM = "user.address";
    public static final String DEFAULT_USER_PHONE_NUMBER_JSP_PARAM = "default.user.phoneNumber";
    public static final String USER_PHONE_NUMBER_JSP_PARAM = "user.phoneNumber";

    public static final String DISH_JSP_ATTRIBUTE = "dish";
    public static final String DISH_NAME_JSP_ATTRIBUTE = "dish.name";
    public static final String DISH_COST_JSP_ATTRIBUTE = "dish.cost";
    public static final String DISH_CATEGORY_JSP_ATTRIBUTE = "dish.category";
    public static final String DISH_DESCRIPTION_JSP_ATTRIBUTE = "dish.description";
    public static final String DISH_PICTURE_JSP_ATTRIBUTE = "dish.picture";
    public static final String DISHES_JSP_ATTRIBUTE = "dishes";


    public static final String CATEGORY_JSP_ATTRIBUTE = "category";
    public static final String CATEGORY_LIST_JSP_ATTRIBUTE = "categoryList";

    public static final String ERROR_JSP_ATTRIBUTE = "error";
    public static final String MESSAGE_JSP_ATTRIBUTE = "message";

    public static final String QUERY_COMMAND_PARAM = "_command";
    public static final String QUERY_CATEGORY_PARAM = "_category";

    public static final String COMMAND_SECURITY_PROPERTY = "command.";
    public static final String COOKIE_NAME_LANG = "lang";

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

//    user@domain.com :       true
//    user@domain.co.in :     true
//    user1@domain.com :      true
//    user.name@domain.com :  true
//    user#@domain.co.in :    true
//    user@domaincom :        true
//
//    user#domain.com :       false
//    @yahoo.com :            false
    public static final String EMAIL_PATTERN = "^(.+)@(.+)$";

//    ^                 # start-of-string
//    (?=.*[0-9])       # a digit must occur at least once
//    (?=.*[a-z])       # a lower case letter must occur at least once
//    (?=.*[A-Z])       # an upper case letter must occur at least once
//    (?=.*[@#$%^&+=])  # a special character must occur at least once
//    (?=\S+$)          # no whitespace allowed in the entire string
//    .{8,}             # anything, at least eight places though
//    $                 # end-of-string
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

// +375291234567
    public static final String PHONE_NUMBER_PATTERN = "^\\+\\d{12}$";


}
