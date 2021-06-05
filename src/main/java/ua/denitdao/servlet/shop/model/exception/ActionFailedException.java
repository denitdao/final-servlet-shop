package ua.denitdao.servlet.shop.model.exception;

import java.util.Collections;
import java.util.List;

/**
 * Used to return to the same page with the message.
 */
public class ActionFailedException extends RuntimeException {

    private final List<String> messageList;

    public ActionFailedException(String message, String messageCode) {
        super(message);
        messageList = Collections.singletonList(messageCode);
    }

    public ActionFailedException(String message, List<String> messageList) {
        super(message);
        this.messageList = messageList;
    }

    public List<String> getMessageList() {
        return messageList;
    }
}
