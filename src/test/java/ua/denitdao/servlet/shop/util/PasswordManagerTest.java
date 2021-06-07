package ua.denitdao.servlet.shop.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class PasswordManagerTest {

    @Test
    void When_toBasicCurrencyEn_Expect_Same() {
        Locale testLocale = new Locale("en");
        BigDecimal testPrice = BigDecimal.valueOf(100);

        assertEquals(PriceConverter.toBasicCurrency(testPrice, testLocale), testPrice);
    }

    @Test
    void When_toBasicCurrencyUk_Expect_Changed() {
        Locale testLocale = new Locale("uk");
        BigDecimal testPrice = BigDecimal.valueOf(100);

        assertEquals(PriceConverter.toBasicCurrency(testPrice, testLocale),
                testPrice.divide(BigDecimal.valueOf(PriceConverter.RATE_USD_TO_UAH), BigDecimal.ROUND_HALF_UP));
    }

    @Test
    void When_fromBasicCurrencyEn_Expect_Same() {
        Locale testLocale = new Locale("en");
        BigDecimal testPrice = BigDecimal.valueOf(100);

        assertEquals(PriceConverter.fromBasicCurrency(testPrice, testLocale), testPrice);
    }

    @Test
    void When_fromBasicCurrencyUk_Expect_Changed() {
        Locale testLocale = new Locale("uk");
        BigDecimal testPrice = BigDecimal.valueOf(100);

        assertEquals(PriceConverter.fromBasicCurrency(testPrice, testLocale),
                testPrice.multiply(BigDecimal.valueOf(PriceConverter.RATE_USD_TO_UAH)));
    }
}