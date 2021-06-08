package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;
import ua.denitdao.servlet.shop.model.util.Pageable;
import ua.denitdao.servlet.shop.model.util.Sort;
import ua.denitdao.servlet.shop.util.PriceConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for mapping request parameters to objects
 */
public final class RequestMapper {

    private static final Logger logger = LogManager.getLogger(RequestMapper.class);

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
     * Build pageable with all the data about sorting parameters provided
     */
    public static Pageable buildPageable(HttpServletRequest req, Locale locale) {
        SortingOrder sortingOrder = SortingOrder.valueOf(Optional.ofNullable(req.getParameter("sorting_order"))
                .orElse(SortingOrder.ASC.toString()));
        SortingParam sortingParam = SortingParam.valueOf(Optional.ofNullable(req.getParameter("sorting_param"))
                .orElse(SortingParam.TITLE.toString()));

        BigDecimal priceLimit = PriceConverter.fromBasicCurrency(new BigDecimal(10000), locale);
        BigDecimal priceMin = new BigDecimal(Optional.ofNullable(req.getParameter("price_min"))
                .orElse("0"));
        BigDecimal priceMax = new BigDecimal(Optional.ofNullable(req.getParameter("price_max"))
                .orElse(priceLimit.toString()));
        priceMax = priceMax.min(priceLimit);

        int currentPage = Integer.parseInt(Optional.ofNullable(req.getParameter("page"))
                .orElse("1"));
        Sort sort = new Sort(sortingOrder, sortingParam,priceMin, priceMax);

        return new Pageable((currentPage <= 0) ? 1 : currentPage, Pageable.PAGE_SIZE, sort);
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

    private RequestMapper() {
    }
}
