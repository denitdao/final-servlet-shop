package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

public class ViewUpdateProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewUpdateProductCommand.class);
    private final ProductService productService;

    public ViewUpdateProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        Long id = Long.valueOf(req.getParameter("id"));
        req.setAttribute("product", productService.getLocalizedProductById(id, new String[] {"en", "uk"}));
        return Paths.UPDATE_PRODUCT_JSP;
    }

}
