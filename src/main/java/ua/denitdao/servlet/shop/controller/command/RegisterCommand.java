package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.exception.EmptyFieldException;
import ua.denitdao.servlet.shop.model.exception.InvalidValueException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.service.UserService;
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.SessionUtil;
import ua.denitdao.servlet.shop.util.Validator;

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
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        HttpSession session = req.getSession();
        try {
            Validator.validateNonEmptyRequest(req);
            Validator.validateNewUserRequest(req);

            String firstName = req.getParameter("firstName");
            String secondName = req.getParameter("secondName");
            String login = req.getParameter("login");
            String password = req.getParameter("password");

            User user = User.builder()
                    .firstName(firstName)
                    .secondName(secondName)
                    .login(login)
                    .password(password).build();

            if (userService.createUser(user)) {
                ContextUtil.addUserToContext(req, user.getId());

                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                Cart cart = (Cart) session.getAttribute("cart");
                if (cartService.syncCart(user.getId(), cart))
                    session.setAttribute("cart", cart);

                logger.info("registered: sess({}) | login({})", session, login);
                return "redirect:" + Paths.VIEW_HOME;
            } else {
                session.setAttribute("errorMessage", "Such user already exists");
            }
        } catch (InvalidValueException | EmptyFieldException e) {
            session.setAttribute("errorMessage", e.getMessage());
        }
        SessionUtil.addRequestParametersToSession(req.getSession(), req, "prev_params");
        return "redirect:" + req.getHeader("referer");
    }
}
