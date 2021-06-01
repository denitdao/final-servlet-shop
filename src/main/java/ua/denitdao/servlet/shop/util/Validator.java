package ua.denitdao.servlet.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.exception.EmptyFieldException;
import ua.denitdao.servlet.shop.model.exception.InvalidValueException;

import java.math.BigDecimal;
import java.util.Map;

public final class Validator {

    private Validator() {
    }

    public static void validateProductRequest(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> parameterMap = req.getParameterMap();

        parameterMap.forEach((key, value) -> {
            if (value[0] == null || value[0].trim().isEmpty()) {
                sb.append("Field can't be empty: ").append(key).append("\n");
            }
        });

        if (sb.length() > 0)
            throw new EmptyFieldException(sb.toString());
    }

    public static void validateProduct(Product product) {
        StringBuilder sb = new StringBuilder();
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            sb.append("Invalid price value\n");
        if (product.getHeight() <= 0)
            sb.append("Invalid height value\n");

        if (sb.length() > 0)
            throw new InvalidValueException(sb.toString());
    }

}
