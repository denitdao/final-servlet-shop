package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.ExceptionMessages;
import ua.denitdao.servlet.shop.util.Paths;

public class DeleteProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DeleteProductCommand.class);
    private final ProductService productService;

    public DeleteProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        long productId = Long.parseLong(req.getParameter("product_id"));
        long categoryId = Long.parseLong(req.getParameter("category_id"));

        if (!productService.delete(productId))
            throw new ActionFailedException("Could not delete product", ExceptionMessages.FAIL_DELETE_PRODUCT);

        return "redirect:" + Paths.VIEW_CATEGORY + "?id=" + categoryId;
    }
}
