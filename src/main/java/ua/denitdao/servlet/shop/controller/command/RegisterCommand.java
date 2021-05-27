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

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private final UserService userService;

    public RegisterCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        User user = User.builder()
                .firstName(firstName)
                .secondName(secondName)
                .login(login)
                .password(password).build();
//        User user = userService.create(login);
        if (user != null && PasswordManager.verifyPassword(password, user.getPassword())) {
            // todo: add validation if user is registered in the context
            ContextUtil.addUserToContext(req, user.getId());
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
}
