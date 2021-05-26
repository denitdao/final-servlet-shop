package ua.denitdao.servlet.shop.controller.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ua.denitdao.servlet.shop.model.entity.User;

import java.util.HashSet;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // todo: create cart for the user!
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User) se.getSession()
                .getAttribute("user");

        if (user != null) {
            @SuppressWarnings("unchecked")
            HashSet<Integer> activeUsers = (HashSet<Integer>) se.getSession()
                    .getServletContext()
                    .getAttribute("activeUsers");
            activeUsers.remove(user.getId());
            se.getSession().getServletContext()
                    .setAttribute("activeUsers", activeUsers);
        }
    }
}
