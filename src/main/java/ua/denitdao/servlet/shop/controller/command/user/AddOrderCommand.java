package ua.denitdao.servlet.shop.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.OrderService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.ExceptionMessages;

public class AddOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddOrderCommand.class);
    private final OrderService orderService;

    public AddOrderCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        orderService = serviceFactory.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        HttpSession session = req.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart.getProducts().size() == 0)
            throw new ActionFailedException("Cart is empty", ExceptionMessages.FAIL_CART_EMPTY);

        User user = (User) session.getAttribute("user");
        if (!orderService.makeOrder(user.getId(), cart))
            throw new ActionFailedException("Can't make an order", ExceptionMessages.FAIL_CREATE_ORDER);

        session.setAttribute("cart", cart);
        return "redirect:" + req.getHeader("referer");
    }

}
