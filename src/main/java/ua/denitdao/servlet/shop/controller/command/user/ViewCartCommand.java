package ua.denitdao.servlet.shop.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.List;
import java.util.Locale;

public class ViewCartCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewCartCommand.class);
    CartService cartService;

    public ViewCartCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        cartService = serviceFactory.getCartService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        User user = (User) req.getSession().getAttribute("user");
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        List<OrderProduct> orderProducts = cartService.getProductsInCart(user.getId(), locale.toString());

        req.setAttribute("orderProducts", orderProducts);

        return Paths.CART_JSP;
    }
}
