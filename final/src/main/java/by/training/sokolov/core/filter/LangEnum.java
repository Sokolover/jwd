package by.training.sokolov.core.filter;

public enum LangEnum {

    RUSSIAN("ru"),
    ENGLISH("en"),
    DEFAULT("default");

    private String value;

    LangEnum(String value) {
        this.value = value;
    }

    public static LangEnum fromString(String name) {
        final LangEnum[] values = LangEnum.values();
        for (LangEnum gemEnumElement : values) {
            if (gemEnumElement.value.equalsIgnoreCase(name) || gemEnumElement.name().equalsIgnoreCase(name)) {
                return gemEnumElement;
            }
        }
        return DEFAULT;
    }

    public String getValue() {
        return value;
    }
}
