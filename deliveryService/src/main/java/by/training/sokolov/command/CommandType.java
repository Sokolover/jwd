package by.training.sokolov.command;

import java.util.Optional;
import java.util.stream.Stream;

public enum CommandType {

    INDEX,
    LOGIN_SUBMIT,
    LOGOUT,
    REGISTER_USER,

    LOGIN_SERVLET_SWITCH,
    REGISTER_SERVLET_SWITCH,
    MENU_SERVLET_SWITCH,
    ORDER_BASKET_SERVLET_SWITCH,
    ORDER_CHECKOUT_SERVLET_SWITCH,

    DELETE_DISH_FROM_ORDER,
    ADD_ITEM_TO_ORDER,
    ORDER_ITEM_LIST_DISPLAY,
    CREATE_ORDER,
    DELETE_ORDER,
    CHECKOUT_ORDER_FORM_DISPLAY,
    CHECKOUT_ORDER_FORM_SUBMIT,

    CREATE_DISH_FORM_DISPLAY,
    CREATE_DISH_FORM_SUBMIT,
    UPDATE_DISH_FORM_DISPLAY,
    UPDATE_DISH_FORM_SUBMIT,
    DELETE_DISH_FROM_MENU,

    CREATE_DISH_CATEGORY_FORM_DISPLAY,
    CREATE_DISH_CATEGORY_FORM_SUBMIT,

    DISH_FEEDBACK_WRITE,
    DISH_FEEDBACK_SUBMIT,
    DISH_MENU_DISPLAY,

    DEFAULT;

    public static Optional<CommandType> of(String name) {
        return Stream.of(CommandType.values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findFirst();
    }
}
