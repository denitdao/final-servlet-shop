package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.enums.Status;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.OrderService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Locale;

public class ViewOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewOrderCommand.class);
    OrderService orderService;

    public ViewOrderCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        orderService = serviceFactory.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        Long id = Long.valueOf(req.getParameter("id"));

        Locale locale = (Locale) req.getSession().getAttribute("locale");
        Order order = orderService.getOrder(id, locale.toString())
                .orElseThrow(() -> new RuntimeException("No such order exists."));

        req.setAttribute("order", order);
        Status[] statuses = { Status.REGISTERED, Status.DELIVERED, Status.CANCELLED };
        req.setAttribute("statuses", statuses);

        return Paths.ORDER_JSP;
    }
}
