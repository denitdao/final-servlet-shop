package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.EmptyFieldException;
import ua.denitdao.servlet.shop.model.exception.InvalidValueException;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
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
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        HttpSession session = req.getSession();
        try {
            Validator.validateNonEmptyRequest(req);

            String login = req.getParameter("login");
            String password = req.getParameter("password");

            User user = userService.getUserByLogin(login)
                    .orElseThrow(() -> new InvalidValueException("No such login exists"));

            if (!PasswordManager.verifyPassword(password, user.getPassword()))
                throw new InvalidValueException("Wrong password");
            user.setPassword(null);
            if (user.isBlocked())
                throw new ActionFailedException("You were blocked by admin");

            if (ContextUtil.findUserInContext(req, user.getId()))
                throw new ActionFailedException("You are already logged in");

            ContextUtil.addUserToContext(req, user.getId());

            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            Cart cart = (Cart) session.getAttribute("cart");
            if (cartService.syncCart(user.getId(), cart))
                session.setAttribute("cart", cart);

            logger.info("logged in: sess({}) | login({})", session, login);
            return "redirect:" + Paths.VIEW_HOME;
        } catch (InvalidValueException | EmptyFieldException | ActionFailedException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        SessionUtil.addRequestParametersToSession(req.getSession(), req, "prev_params");
        return "redirect:" + req.getHeader("referer");
    }


}
