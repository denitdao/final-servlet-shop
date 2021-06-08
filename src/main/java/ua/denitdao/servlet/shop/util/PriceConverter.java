package ua.denitdao.servlet.shop.util;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Utility class for currency conversions. The default currency is USD
 */
public final class PriceConverter {

    public static final int RATE_USD_TO_UAH = 27;

    /**
     * Convert any supported currency to USD
     */
    public static BigDecimal toBasicCurrency(BigDecimal price, Locale locale) {
        return "uk".equals(locale.toString()) ? price.divide(BigDecimal.valueOf(RATE_USD_TO_UAH), BigDecimal.ROUND_HALF_UP) : price;
    }

    /**
     * Convert USD into any supported currency
     */
    public static BigDecimal fromBasicCurrency(BigDecimal price, Locale locale) {
        return "uk".equals(locale.toString()) ? price.multiply(BigDecimal.valueOf(RATE_USD_TO_UAH)) : price;
    }

    private PriceConverter() {
    }
}
