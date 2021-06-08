package ua.denitdao.servlet.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Util class for the operations with objects stored in the session
 */
public final class SessionUtil {

    private SessionUtil() {
    }

    /**
     * Return all of the parameters passed in the request to the session
     */
    public static void addRequestParametersToSession(HttpSession sess, HttpServletRequest req, String name) {
        sess.setAttribute(name, req.getParameterMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue()[0])));
    }

}
