package by.training.sokolov.command.constants;

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
    ORDER_ITEM_ADD,
    VIEW_ORDER_DISH_LIST,
    CREATE_ORDER,
    CHECKOUT_ORDER_FORM_DISPLAY,
    CHECKOUT_ORDER_FORM_SUBMIT,

    DISH_FEEDBACK_WRITE,
    DISH_FEEDBACK_SUBMIT,
    VIEW_DISH_MENU,
    VIEW_USER_LIST;

    /*
    TODO СДЕЛАТЬ КАК в старых проектах
     */
    public static Optional<CommandType> of(String name) {
        return Stream.of(CommandType.values())
                .filter(type -> type.name().equalsIgnoreCase(name))
                .findFirst();
    }
}
