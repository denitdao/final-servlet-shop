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
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.PasswordManager;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Optional;

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

        Optional<User> userOpt = userService.getUserByLogin(login);
        if (userOpt.isPresent() && PasswordManager.verifyPassword(password, userOpt.get().getPassword())) {
            User user = userOpt.get();

            if (ContextUtil.findUserInContext(req, user.getId())) {
                session.setAttribute("login_status", "You are already logged in");
                session.setAttribute("wrong_login", login);
                return "redirect:" + Paths.VIEW_LOGIN;
            }

            ContextUtil.addUserToContext(req, user.getId());

            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            logger.info("logged in: sess({}) | login({})", session, login);
            return "redirect:" + Paths.VIEW_HOME;
        }

        session.setAttribute("login_status", "failed");
        session.setAttribute("wrong_login", login);
        return "redirect:" + Paths.VIEW_LOGIN;
    }


}
