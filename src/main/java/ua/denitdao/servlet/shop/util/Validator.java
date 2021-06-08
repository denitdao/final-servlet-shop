package ua.denitdao.servlet.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Util class for validation of the request parameters or entity fields
 */
public final class Validator {

    private Validator() {
    }

    /**
     * Check if every passed parameter has a not empty value. Throws exception if empty
     */
    public static void validateNonEmptyRequest(HttpServletRequest req) throws ValidationException {
        Map<String, String[]> parameterMap = req.getParameterMap();

        parameterMap.forEach((key, value) -> {
            if (value[0] == null || value[0].trim().isEmpty())
                throw new ValidationException("Empty field", ExceptionMessages.EMPTY_FIELD);
        });
    }

    /**
     * Check if product parameters are correct type
     */
    public static void validateProduct(Product product) throws ValidationException {
        List<String> messages = new ArrayList<>();
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            messages.add(ExceptionMessages.WRONG_PRICE);
        if (product.getWeight() <= 0)
            messages.add(ExceptionMessages.WRONG_WEIGHT);

        if (!messages.isEmpty())
            throw new ValidationException("Wrong product value", messages);
    }

    /**
     * Check if user parameters contain enough symbols
     */
    public static void validateNewUser(User user) throws ValidationException {
        List<String> messages = new ArrayList<>();
        if (user.getFirstName().trim().length() < 3)
            messages.add(ExceptionMessages.INVAL_FIRSTNAME);
        if (user.getSecondName().trim().length() < 3)
            messages.add(ExceptionMessages.INVAL_SECONDNAME);
        if (user.getLogin().trim().length() < 3)
            messages.add(ExceptionMessages.INVAL_LOGIN);
        if (user.getPassword().trim().length() < 3)
            messages.add(ExceptionMessages.INVAL_PASSWORD);

        if (!messages.isEmpty())
            throw new ValidationException("Invalid user value", messages);
    }
}
