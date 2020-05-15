package by.training.sokolov.task3.controller.commands;

public enum CommandEnum {
    DOM_COMMAND("dom"),
    SAX_COMMAND("sax"),
    STAX_COMMAND("stax"),
    DEFAULT_COMMAND("default"),
    SHOW_COMMAND("show"),
    DELETE_COMMAND("delete");

    private String value;

    public String getValue() {
        return value;
    }

    CommandEnum(String value) {
        this.value = value;
    }

    public static CommandEnum fromString(String name) {
        final CommandEnum[] values = CommandEnum.values();
        for (CommandEnum commandEnumElement : values) {
            if (commandEnumElement.value.equals(name) || commandEnumElement.name().equals(name)) {
                return commandEnumElement;
            }
        }
        return CommandEnum.DEFAULT_COMMAND;
    }
}
