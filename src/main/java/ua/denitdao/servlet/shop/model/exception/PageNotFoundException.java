package ua.denitdao.servlet.shop.model.exception;

/**
 * Used to return to the error page with custom message.
 */
public class PageNotFoundException extends RuntimeException {

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
