package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.Paths;

public class LogoutCommand implements Command {

    private final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        HttpSession session = req.getSession();
        logger.info("logged out: sess({})", session);

        User user = (User) session.getAttribute("user");
        if (user != null)
            ContextUtil.removeUserFromContext(req, user.getId());

        session.removeAttribute("user");
        session.removeAttribute("role");
        session.removeAttribute("cart");

        return "redirect:" + Paths.VIEW_HOME;
    }
}
