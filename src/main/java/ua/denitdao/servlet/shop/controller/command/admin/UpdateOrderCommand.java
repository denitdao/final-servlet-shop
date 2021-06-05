package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.enums.Status;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.OrderService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.ExceptionMessages;

public class UpdateOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateOrderCommand.class);
    private final OrderService orderService;

    public UpdateOrderCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        orderService = serviceFactory.getOrderService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        Long id = Long.valueOf(req.getParameter("id"));
        Status status = Status.valueOf(req.getParameter("status"));

        if (!orderService.updateOrder(id, status))
            throw new ActionFailedException("Order update failed", ExceptionMessages.FAIL_UPDATE_PRODUCT);

        return "redirect:" + req.getHeader("referer");
    }

}
