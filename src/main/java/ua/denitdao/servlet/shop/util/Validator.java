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

    public static void validateNonEmptyRequest(HttpServletRequest req) throws EmptyFieldException {
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

    public static void validateProduct(Product product) throws InvalidValueException {
        StringBuilder sb = new StringBuilder();
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            sb.append("Invalid price value\n");
        if (product.getHeight() <= 0)
            sb.append("Invalid height value\n");

        if (sb.length() > 0)
            throw new InvalidValueException(sb.toString());
    }

    public static void validateNewUserRequest(HttpServletRequest req) throws InvalidValueException {
        StringBuilder sb = new StringBuilder();
        if (req.getParameter("firstName").trim().length() < 2)
            sb.append("First name is too short\n");
        if (req.getParameter("secondName").trim().length() < 2)
            sb.append("Second name is too short\n");
        if (req.getParameter("login").trim().length() < 2)
            sb.append("Login is too short\n");
        if (req.getParameter("password").trim().length() < 2)
            sb.append("Password is too short\n");

        if (sb.length() > 0)
            throw new InvalidValueException(sb.toString());
    }
}
