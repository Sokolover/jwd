package by.training.sokolov.command;

public class CommandProcessException extends RuntimeException {

    private static final long serialVersionUID = -2231934072516221301L;

    public CommandProcessException(String message) {
        super(message);
    }

    public CommandProcessException(String message, Exception root) {
        super(message, root);
    }
}
