package by.daw.api.db.exceptions;

public class DuplicatedPostException extends RuntimeException {
    public DuplicatedPostException() {
    }

    public DuplicatedPostException(String message) {
        super(message);
    }

    public DuplicatedPostException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedPostException(Throwable cause) {
        super(cause);
    }

    public DuplicatedPostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
