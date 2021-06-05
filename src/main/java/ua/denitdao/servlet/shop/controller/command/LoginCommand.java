package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ValidationException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.service.UserService;
import ua.denitdao.servlet.shop.util.*;

public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private final UserService userService;
    private final CartService cartService;

    public LoginCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
        cartService = serviceFactory.getCartService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        Validator.validateNonEmptyRequest(req);

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userService.getUserByLogin(login)
                .orElseThrow(() -> new ValidationException("User doesn't exist", ExceptionMessages.WRONG_LOGIN));

        if (!PasswordManager.verifyPassword(password, user.getPassword()))
            throw new ValidationException("Wrong password", ExceptionMessages.WRONG_PASSWORD);

        if (user.isBlocked())
            throw new ValidationException("Blocked by admin", ExceptionMessages.USER_BLOCKED);

        if (ContextUtil.findUserInContext(req, user.getId()))
            throw new ValidationException("Already logged in", ExceptionMessages.USER_LOGGED);

        user.setPassword(null);
        ContextUtil.addUserToContext(req, user.getId());
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());
        Cart cart = (Cart) session.getAttribute("cart");
        if (cartService.syncCart(user.getId(), cart))
            session.setAttribute("cart", cart);

        logger.info("logged in: sess({}) | login({})", session, user.getLogin());
        return "redirect:" + Paths.VIEW_HOME;
    }
}
