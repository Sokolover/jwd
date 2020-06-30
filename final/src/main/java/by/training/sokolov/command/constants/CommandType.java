package by.training.sokolov.command.constants;

import java.util.Optional;
import java.util.stream.Stream;

public enum CommandType {

    INDEX,
    LOGIN_SERVLET_SWITCH,
    LOGIN_SUBMIT,
    LOGOUT,
    REGISTER_SERVLET_SWITCH,
    REGISTER_USER,
    MENU_SERVLET_SWITCH,
    VIEW_DISH_MENU,
    ORDER_ITEM_ADD,
    ORDER_BASKET_SERVLET_SWITCH,
    ORDER_DISH_LIST_DISPLAY,
    CREATE_ORDER,

    DELETE_DISH_FROM_ORDER,
    //    DISH_CATEGORY_DISPLAY,
//    DISH_CATEGORY_SUBMIT,
    VIEW_USER_LIST,
    VIEW_USER_DETAILS,
    CREATE_USER,
    EDIT_VIEW_USER,
    EDIT_SAVE_USER,
    DELETE_USER;

    public static Optional<CommandType> of(String name) {
        return Stream.of(CommandType.values()).filter(type -> type.name().equalsIgnoreCase(name)).findFirst();
    }
}
