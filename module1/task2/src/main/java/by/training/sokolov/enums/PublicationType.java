package by.training.sokolov.enums;

import by.training.sokolov.command.TypeNotExistException;

public enum PublicationType {
    BOOK("book"),
    MAGAZINE("magazine"),
    ALBUM("album"),
    DEFAULT("");

    private final String typeName;

    PublicationType(String s) {
        this.typeName = s;
    }

    public static PublicationType fromString(String name) throws TypeNotExistException {

        final PublicationType[] values = PublicationType.values();
        for (PublicationType type : values) {
            if (type.typeName.equals(name) || type.name().equals(name)) {
                return type;
            }
        }
        throw new TypeNotExistException("error in method fromString() - " + PublicationType.class.getName());
    }
}
