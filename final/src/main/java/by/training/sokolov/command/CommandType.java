package by.training.sokolov.command;

import java.util.Optional;
import java.util.stream.Stream;

public enum CommandType {

    INDEX,
    LOGIN_DISPLAY,
    LOGIN_SUBMIT,
    LOGOUT,
    REGISTER_DISPLAY,
    REGISTER_SUBMIT,
    DISH_MENU_DISPLAY,
    DISH_MENU_SUBMIT,
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
