package by.training.sokolov.parser;

public enum ParserName {

    DOM("dom"),
    SAX("sax"),
    STAX("stax"),
    DEFAULT_PARSER("default");

    private String value;

    public String getValue() {
        return value;
    }

    ParserName(String value) {
        this.value = value;
    }

    public static ParserName fromString(String name) {
        final ParserName[] values = ParserName.values();
        for (ParserName parser : values) {
            if (parser.value.equals(name) || parser.name().equals(name)) {
                return parser;
            }
        }
        return ParserName.DEFAULT_PARSER;
    }
}
