package by.training.sokolov.validation;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1981063800538652933L;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
