package ua.denitdao.servlet.shop.controller.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.util.ContextUtil;

import java.util.LinkedHashMap;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("cart", new Cart(new LinkedHashMap<>()));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User) se.getSession()
                .getAttribute("user");

        if (user != null) {
            ContextUtil.removeUserFromContext(se, user.getId());
        }
    }
}
