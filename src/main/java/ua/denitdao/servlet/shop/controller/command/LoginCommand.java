package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.service.UserService;
import ua.denitdao.servlet.shop.util.PasswordManager;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.HashSet;

public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private final UserService userService;

    public LoginCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        User user = userService.getUserByLogin(login);
        if (user != null && PasswordManager.verifyPassword(password, user.getPassword())) {
            // todo: add validation if user is registered in the context
            addUserToContext(req, user.getId());
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            logger.info("logged in: sess({}) | login({})", session, login);
            return "redirect:" + Paths.VIEW_HOME;
        } else {
            session.setAttribute("login_status", "failed");
            session.setAttribute("wrong_login", login);
            return "redirect:" + Paths.VIEW_LOGIN;
        }
    }

    private void addUserToContext(HttpServletRequest req, Integer userId) {
        @SuppressWarnings("unchecked")
        HashSet<Integer> activeUsers = (HashSet<Integer>) req
                .getServletContext()
                .getAttribute("activeUsers");
        activeUsers.add(userId);
        req.getServletContext()
                .setAttribute("activeUsers", activeUsers);
    }
}
