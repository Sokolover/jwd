package by.training.sokolov.parser;


import java.util.List;

public class ParseResult<T> {

    private List<T> parseResults;
    private String message;

    public ParseResult(List<T> parseResults, String message) {
        this.parseResults = parseResults;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getParseResults() {
        return parseResults;
    }

}
