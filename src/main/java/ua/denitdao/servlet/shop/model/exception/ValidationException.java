package ua.denitdao.servlet.shop.model.exception;

/**
 * Used to return to the same page with all the data back.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
