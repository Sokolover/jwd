package by.training.sokolov.database.connection;

public class ConnectionException extends Exception {

    private static final long serialVersionUID = 1957440137770481813L;

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
