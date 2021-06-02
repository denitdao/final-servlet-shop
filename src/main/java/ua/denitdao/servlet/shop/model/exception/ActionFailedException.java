package ua.denitdao.servlet.shop.model.exception;

public class ActionFailedException extends RuntimeException {
    public ActionFailedException(String message) {
        super(message);
    }

    public ActionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
