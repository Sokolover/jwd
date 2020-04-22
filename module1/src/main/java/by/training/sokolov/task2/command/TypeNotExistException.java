package by.training.sokolov.task2.command;

public class TypeNotExistException extends Exception {

    public TypeNotExistException() {

    }

    public TypeNotExistException(String message) {
        super(message);
    }

    public TypeNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeNotExistException(Throwable cause) {
        super(cause);
    }

    public TypeNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
