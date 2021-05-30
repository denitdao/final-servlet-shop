package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.SessionUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(UpdateProductCommand.class);
    private final ProductService productService;

    public UpdateProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        long id = Long.parseLong(req.getParameter("id"));
        Map<String, Product> localizedProduct = new HashMap<>();

        // todo: validate fields

        localizedProduct.put("en", getProductFromRequest(req, "en"));
        localizedProduct.put("uk", getProductFromRequest(req, "uk"));

        if (productService.update(localizedProduct)) {
            return "redirect:" + Paths.VIEW_PRODUCT + "?id=" + id;
        } else {
            SessionUtil.addRequestParametersToSession(req.getSession(), req, "prev_params");
            req.getSession().setAttribute("errorMessage", "Invalid parameters");
            return "redirect:" + req.getHeader("referer");
        }
    }

    /**
     * Construct product from the request parameters using specified locale.
     */
    private Product getProductFromRequest(HttpServletRequest req, String locale) {
        Product product = Product.builder()
                .id(Long.valueOf(req.getParameter("id")))
                .color(req.getParameter("color_" + locale))
                .title(req.getParameter("title_" + locale))
                .description(req.getParameter("description_" + locale))
                .height(Double.valueOf(req.getParameter("height")))
                .price(BigDecimal.valueOf(Long.parseLong(req.getParameter("price"))))
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
