package by.training.sokolov.task3.model;

public enum GemEnum {

    GEMS("gems"),
    GEM("gem"),
    ID("id"),
    VISUAL_PARAMS("visualParams"),
    PRECIOUSNESS("preciousness"),
    ORIGIN("origin"),
    NAME("name"),
    COLOR("color"),
    TRANSPARENCY("transparency"),
    NUMBER_OF_FACES("numberOfFaces"),
    VALUE("value"),
    DEFAULT("default");

    private String value;

    public String getValue() {
        return value;
    }

    GemEnum(String value) {
        this.value = value;
    }

    public static GemEnum fromString(String name) {
        final GemEnum[] values = GemEnum.values();
        for (GemEnum gemEnumElement : values) {
            if (gemEnumElement.value.equalsIgnoreCase(name) || gemEnumElement.name().equalsIgnoreCase(name)) {
                return gemEnumElement;
            }
        }
        return DEFAULT;
    }
}
