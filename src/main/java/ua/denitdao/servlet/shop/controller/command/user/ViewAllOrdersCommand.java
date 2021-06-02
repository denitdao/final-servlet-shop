package ua.denitdao.servlet.shop.controller.command.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.Roles;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.OrderService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.List;

public class ViewAllOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewAllOrdersCommand.class);
    private final OrderService orderService;

    public ViewAllOrdersCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        orderService = serviceFactory.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        List<Order> orders;
        User user = (User) req.getSession().getAttribute("user");

        if (Roles.ADMIN.toString().equals(user.getRole())) {
            if (req.getParameter("user_id") != null) {
                Long id = Long.valueOf(req.getParameter("user_id"));
                orders = orderService.getAllOfUser(id);
            } else {
                orders = orderService.getAll();
            }
        } else {
            orders = orderService.getAllOfUser(user.getId());
        }

        req.setAttribute("orders", orders);
        return Paths.ALL_ORDERS_JSP;
    }
}
