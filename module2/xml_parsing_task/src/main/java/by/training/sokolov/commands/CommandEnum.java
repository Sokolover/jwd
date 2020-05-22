package by.training.sokolov.commands;

public enum CommandEnum {

    UPLOAD_COMMAND("upload"),
    DOWNLOAD_COMMAND("download"),
    DELETE_ALL_COMMAND("delete_all"),
    DEFAULT_COMMAND("default");

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
