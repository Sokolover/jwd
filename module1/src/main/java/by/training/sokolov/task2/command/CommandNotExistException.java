package by.training.sokolov.task2.command;

public class CommandNotExistException extends Exception {

    public CommandNotExistException() {

    }

    public CommandNotExistException(String message) {
        super(message);
    }

    public CommandNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotExistException(Throwable cause) {
        super(cause);
    }

    public CommandNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
