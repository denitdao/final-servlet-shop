package ua.denitdao.servlet.shop.model.exception;

import java.util.Collections;
import java.util.List;

/**
 * Used to return to the same page with all the data back.
 */
public class ValidationException extends RuntimeException {

    private final List<String> messageList;

    public ValidationException(String message, String messageCode) {
        super(message);
        messageList = Collections.singletonList(messageCode);
    }

    public ValidationException(String message, List<String> messageList) {
        super(message);
        this.messageList = messageList;
    }

    public List<String> getMessageList() {
        return messageList;
    }
}
