package ua.denitdao.servlet.shop.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.LinkedHashMap;

public class LogoutCommand implements Command {

    private final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        logger.info("logged out: sess({})", session);

        User user = (User) session.getAttribute("user");
        if (user != null)
            ContextUtil.removeUserFromContext(req, user.getId());

        session.removeAttribute("user");
        session.removeAttribute("role");
        session.setAttribute("cart", new Cart(new LinkedHashMap<>()));

        return "redirect:" + Paths.VIEW_HOME;
    }
}
