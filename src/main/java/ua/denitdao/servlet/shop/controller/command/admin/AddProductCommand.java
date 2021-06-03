package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.exception.EmptyFieldException;
import ua.denitdao.servlet.shop.model.exception.InvalidValueException;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.SessionUtil;
import ua.denitdao.servlet.shop.util.Validator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class AddProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);
    private final ProductService productService;

    public AddProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        try {
            Validator.validateNonEmptyRequest(req);

            long categoryId = Long.parseLong(req.getParameter("category_id"));
            Map<String, Product> localizedProduct = new HashMap<>();

            localizedProduct.put("uk", getProductFromRequest(req, "uk"));
            Validator.validateProduct(localizedProduct.get("uk"));
            localizedProduct.put("en", getProductFromRequest(req, "en"));
            Validator.validateProduct(localizedProduct.get("en"));

            if (productService.create(categoryId, localizedProduct)) {
                return "redirect:" + Paths.VIEW_CATEGORY + "?id=" + categoryId;
            }
            req.getSession().setAttribute("errorMessage", "Couldn't create product");
        } catch (InvalidValueException | EmptyFieldException e) {
            logger.warn(e.getMessage());
            req.getSession().setAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            logger.warn("Failed creating product -- {}", e.getMessage());
            req.getSession().setAttribute("errorMessage", "Invalid parameters passed");
        }
        SessionUtil.addRequestParametersToSession(req.getSession(), req, "prev_params");
        return "redirect:" + req.getHeader("referer");
    }

    /**
     * Construct product from the request parameters using specified locale.
     */
    private Product getProductFromRequest(HttpServletRequest req, String locale) {
        Product product = Product.builder()
                .color(req.getParameter("color_" + locale))
                .title(req.getParameter("title_" + locale))
                .description(req.getParameter("description_" + locale))
                .weight(Double.valueOf(req.getParameter("weight")))
                .price(new BigDecimal(req.getParameter("price")))
                .build();
        product.setProperties(req.getParameterMap()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().matches("cp_[0-9]+_" + locale))
                .collect(Collectors.toMap(e -> {
                    String key = e.getKey();
                    Long cpId = Long.valueOf(key.substring(key.indexOf("cp_") + 3, key.indexOf("_" + locale)));
                    return new CategoryProperty(cpId);
                }, e -> e.getValue()[0]))
        );
        return product;
    }
}
