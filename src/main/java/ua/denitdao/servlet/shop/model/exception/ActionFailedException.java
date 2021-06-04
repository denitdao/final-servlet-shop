package ua.denitdao.servlet.shop.model.exception;

/**
 * Used to return to the same page with the message.
 */
public class ActionFailedException extends RuntimeException {

    public ActionFailedException(String message) {
        super(message);
    }

    public ActionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
