package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.HashSet;

public class LogoutCommand implements Command {

    private final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        HttpSession session = req.getSession(false);
        logger.info("logged out: sess({})", session);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null)
                removeUserFromContext(req, user.getId());
            session.removeAttribute("user");
            session.removeAttribute("role");
        }

        return "redirect:" + Paths.VIEW_HOME;
    }

    /**
     * Remove user from context to allow signing in with this account.
     */
    private void removeUserFromContext(HttpServletRequest req, Integer userId) {
        @SuppressWarnings("unchecked")
        HashSet<Integer> activeUsers = (HashSet<Integer>) req
                .getServletContext()
                .getAttribute("activeUsers");
        activeUsers.remove(userId);
        req.getServletContext()
                .setAttribute("activeUsers", activeUsers);
    }
}
