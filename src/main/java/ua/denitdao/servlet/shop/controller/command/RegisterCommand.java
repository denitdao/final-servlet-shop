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

public class RegisterCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private final UserService userService;
    private final CartService cartService;

    public RegisterCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
        cartService = serviceFactory.getCartService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        Validator.validateNonEmptyRequest(req);

        User user = RequestMapper.buildRegistrationUser(req);
        Validator.validateNewUser(user);

        HttpSession session = req.getSession();
        user.setPassword(PasswordManager.hashPassword(user.getPassword()));
        if (!userService.createUser(user))
            throw new ValidationException("User already exists", ExceptionMessages.USER_EXISTS);

        user.setPassword(null);
        ContextUtil.addUserToContext(req, user.getId());
        session.setAttribute("user", user);
        session.setAttribute("role", user.getRole());
        Cart cart = (Cart) session.getAttribute("cart");
        if (cartService.syncCart(user.getId(), cart))
            session.setAttribute("cart", cart);

        logger.info("registered: sess({}) | login({})", session, user.getLogin());
        return "redirect:" + Paths.VIEW_HOME;
    }
}
