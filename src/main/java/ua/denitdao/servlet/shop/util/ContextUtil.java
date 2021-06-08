package ua.denitdao.servlet.shop.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionEvent;

import java.util.HashSet;

/**
 * Util class for the operations with objects stored in the application context
 */
public final class ContextUtil {

    public static final String ACTIVE_USERS = "activeUsers";

    private ContextUtil() {
    }

    /**
     * Initialize storage for active users
     */
    public static void createActiveUserStorage(ServletContextEvent sce) {
        HashSet<Long> activeUsers = new HashSet<>();
        sce.getServletContext()
                .setAttribute(ACTIVE_USERS, activeUsers);
    }

    /**
     * Add user to the context to prevent from signing in with this account twice.
     */
    public static void addUserToContext(HttpServletRequest req, Long userId) {
        @SuppressWarnings("unchecked")
        HashSet<Long> activeUsers = (HashSet<Long>) req
                .getServletContext()
                .getAttribute(ACTIVE_USERS);
        activeUsers.add(userId);
        req.getServletContext()
                .setAttribute(ACTIVE_USERS, activeUsers);
    }

    /**
     * Remove user from context to allow signing in with this account.
     */
    public static void removeUserFromContext(HttpServletRequest req, Long userId) {
        @SuppressWarnings("unchecked")
        HashSet<Long> activeUsers = (HashSet<Long>) req
                .getServletContext()
                .getAttribute(ACTIVE_USERS);
        activeUsers.remove(userId);
        req.getServletContext()
                .setAttribute(ACTIVE_USERS, activeUsers);
    }

    public static void removeUserFromContext(HttpSessionEvent se, Long userId) {
        @SuppressWarnings("unchecked")
        HashSet<Long> activeUsers = (HashSet<Long>) se.getSession()
                .getServletContext()
                .getAttribute(ACTIVE_USERS);
        activeUsers.remove(userId);
        se.getSession().getServletContext()
                .setAttribute(ACTIVE_USERS, activeUsers);
    }

    /**
     * Check if user is already logged in the context
     */
    public static boolean findUserInContext(HttpServletRequest req, Long userId) {
        @SuppressWarnings("unchecked")
        HashSet<Long> activeUsers = (HashSet<Long>) req
                .getServletContext()
                .getAttribute(ACTIVE_USERS);
        return activeUsers.contains(userId);
    }
}
