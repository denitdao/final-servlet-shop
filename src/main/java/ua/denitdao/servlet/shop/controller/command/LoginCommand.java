package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.service.UserService;
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.PasswordManager;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.SessionUtil;

import java.util.Optional;

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
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();

        Optional<User> userOpt = userService.getUserByLogin(login);
        if (userOpt.isPresent() && PasswordManager.verifyPassword(password, userOpt.get().getPassword())) {
            User user = userOpt.get();

            if (ContextUtil.findUserInContext(req, user.getId())) {
                session.setAttribute("errorMessage", "You are already logged in");
                SessionUtil.addRequestParametersToSession(req.getSession(), req, "prev_params");
                return "redirect:" + req.getHeader("referer");
            }

            ContextUtil.addUserToContext(req, user.getId());

            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            Cart cart = (Cart) session.getAttribute("cart");
            if (cartService.syncCart(user.getId(), cart))
                session.setAttribute("cart", cart);

            logger.info("logged in: sess({}) | login({})", session, login);
            return "redirect:" + Paths.VIEW_HOME;
        }

        session.setAttribute("errorMessage", "Wrong credentials");
        SessionUtil.addRequestParametersToSession(req.getSession(), req, "prev_params");
        return "redirect:" + req.getHeader("referer");
    }


}
