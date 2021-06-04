package ua.denitdao.servlet.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Map;

public final class Validator {

    private Validator() {
    }

    /**
     * Check if every passed parameter has a not empty value. Throws exception if empty
     */
    public static void validateNonEmptyRequest(HttpServletRequest req) throws ValidationException {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> parameterMap = req.getParameterMap();

        parameterMap.forEach((key, value) -> {
            if (value[0] == null || value[0].trim().isEmpty()) {
                sb.append("Field can't be empty: ").append(key).append("\n");
            }
        });

        if (sb.length() > 0)
            throw new ValidationException(sb.toString());
    }

    /**
     * Check if product parameters are correct type
     */
    public static void validateProduct(Product product) throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            sb.append("Invalid price value\n");
        if (product.getWeight() <= 0)
            sb.append("Invalid weight value\n");

        if (sb.length() > 0)
            throw new ValidationException(sb.toString());
    }

    /**
     * Check if user parameters contain enough symbols
     */
    public static void validateNewUser(User user) throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if (user.getFirstName().trim().length() < 3)
            sb.append("First name is too short (< 3)\n");
        if (user.getSecondName().trim().length() < 3)
            sb.append("Second name is too short (< 3)\n");
        if (user.getLogin().trim().length() < 3)
            sb.append("Login is too short (< 3)\n");
        if (user.getPassword().trim().length() < 3)
            sb.append("Password is too short (< 3)\n");

        if (sb.length() > 0)
            throw new ValidationException(sb.toString());
    }
}
