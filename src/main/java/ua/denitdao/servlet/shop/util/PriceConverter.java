package ua.denitdao.servlet.shop.util;

import java.math.BigDecimal;
import java.util.Locale;

public final class PriceConverter {

    public static final int RATE_USD_TO_UAH = 27;

    public static BigDecimal toBasicCurrency(BigDecimal price, Locale locale) {
        return "uk".equals(locale.toString()) ? price.divide(BigDecimal.valueOf(RATE_USD_TO_UAH), BigDecimal.ROUND_HALF_UP) : price;
    }

    public static BigDecimal fromBasicCurrency(BigDecimal price, Locale locale) {
        return "uk".equals(locale.toString()) ? price.multiply(BigDecimal.valueOf(RATE_USD_TO_UAH)) : price;
    }

    private PriceConverter() {
    }
}
