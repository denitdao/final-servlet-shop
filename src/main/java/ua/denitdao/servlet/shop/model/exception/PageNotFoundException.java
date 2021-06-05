package ua.denitdao.servlet.shop.model.exception;

import java.util.Collections;
import java.util.List;

/**
 * Used to return to the error page with custom message.
 */
public class PageNotFoundException extends RuntimeException {

    private final List<String> messageList;

    public PageNotFoundException(String message, String messageCode) {
        super(message);
        messageList = Collections.singletonList(messageCode);
    }

    public PageNotFoundException(String message, List<String> messageList) {
        super(message);
        this.messageList = messageList;
    }

    public List<String> getMessageList() {
        return messageList;
    }
}
