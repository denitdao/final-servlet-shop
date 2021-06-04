package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.util.PasswordManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class RequestMapper {

    private RequestMapper() {
    }

    /**
     * Build localized user from the request.
     */
    public static User buildRegistrationUser(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        return User.builder()
                .firstName(firstName)
                .secondName(secondName)
                .login(login)
                .password(password).build();
    }

    /**
     * Build localized product map from the request.
     */
    public static Map<String, Product> buildLocalizedProduct(HttpServletRequest req) {
        Map<String, Product> localizedProduct = new HashMap<>();
        localizedProduct.put("uk", getProductFromRequest(req, "uk"));
        localizedProduct.put("en", getProductFromRequest(req, "en"));
        return localizedProduct;
    }

    /**
     * Construct product from the request parameters using specified locale.
     */
    private static Product getProductFromRequest(HttpServletRequest req, String locale) {
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
