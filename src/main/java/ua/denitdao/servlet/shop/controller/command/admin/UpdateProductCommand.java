package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.controller.command.RequestMapper;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.exception.ValidationException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.ExceptionMessages;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.Validator;

import java.util.Map;

public class UpdateProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateProductCommand.class);
    private final ProductService productService;

    public UpdateProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        Validator.validateNonEmptyRequest(req);
        long id = Long.parseLong(req.getParameter("id"));

        Map<String, Product> localizedProduct = RequestMapper.buildLocalizedProduct(req);
        localizedProduct.values()
                .forEach(p -> {
                    p.setId(id);
                    Validator.validateProduct(p);
                });
        if (!productService.update(localizedProduct))
            throw new ValidationException("Failed to edit product", ExceptionMessages.FAIL_UPDATE_PRODUCT);

        return "redirect:" + Paths.VIEW_PRODUCT + "?id=" + id;
    }
}
