package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.Status;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.OrderService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Optional;

public class ViewOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewOrderCommand.class);
    OrderService orderService;

    public ViewOrderCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        orderService = serviceFactory.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        Long id = Long.valueOf(req.getParameter("id"));


        Optional<Order> order = orderService.getOrder(id, req.getLocale().toString());
        if (order.isPresent()) {
            req.setAttribute("order", order.get());
            req.setAttribute("statuses", Status.values());
        } else {
            req.setAttribute("errorMessage", "No such order exists.");
        }

        return Paths.ORDER_JSP;
    }
}
