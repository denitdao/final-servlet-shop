package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;

public class AddToCartCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddToCartCommand.class);
    private final CartService cartService;

    public AddToCartCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        cartService = serviceFactory.getCartService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        long productId = Long.parseLong(req.getParameter("product_id"));
        int amount = Integer.parseInt(req.getParameter("amount"));
        // todo validate amount
        HttpSession session = req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        cart.getProducts().put(productId, amount);

        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (cartService.syncCart(user.getId(), cart))
                session.setAttribute("cart", cart);
            else
                session.setAttribute("errorMessage", "failed to add to cart");
        }
        return "redirect:" + req.getHeader("referer");
    }

}
