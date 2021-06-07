package ua.denitdao.servlet.shop.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ValidationException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidatorTest {

    @Mock
    private HttpServletRequest req;

    @Test
    void When_validateNonEmptyRequest_Expect_Valid() {
        Map<String, String[]> paramMap = new HashMap<>();
        paramMap.put("key", new String[]{"val1", "val2"});
        paramMap.put("key2", new String[]{"val3"});
        when(req.getParameterMap()).thenReturn(paramMap);

        assertDoesNotThrow(() -> Validator.validateNonEmptyRequest(req));
        verify(req).getParameterMap();
    }

    @Test
    void When_validateEmptyStringRequest_Expect_Throw() {
        Map<String, String[]> paramMap = new HashMap<>();
        paramMap.put("key", new String[]{"val1", "val2"});
        paramMap.put("key2", new String[]{""});
        when(req.getParameterMap()).thenReturn(paramMap);

        assertThrows(ValidationException.class, () -> Validator.validateNonEmptyRequest(req));
        verify(req).getParameterMap();
    }

    @Test
    void When_validateEmptyRequest_Expect_Throw() {
        Map<String, String[]> paramMap = new HashMap<>();
        paramMap.put("key", new String[]{"val1", "val2"});
        paramMap.put("key2", new String[]{null});
        when(req.getParameterMap()).thenReturn(paramMap);

        assertThrows(ValidationException.class, () -> Validator.validateNonEmptyRequest(req));
        verify(req).getParameterMap();
    }

    @Test
    void When_validateProduct_Expect_Valid() {
        Product product = Product.builder().price(BigDecimal.ONE).weight(1.02).build();

        assertDoesNotThrow(() -> Validator.validateProduct(product));
    }

    @Test
    void When_validateProductBadPrice_Expect_Throw() {
        Product product = Product.builder().price(BigDecimal.ZERO).weight(1.02).build();

        assertThrows(ValidationException.class, () -> Validator.validateProduct(product));
    }

    @Test
    void When_validateProductBadWeight_Expect_Throw() {
        Product product = Product.builder().price(BigDecimal.ONE).weight(-1.23).build();

        assertThrows(ValidationException.class, () -> Validator.validateProduct(product));
    }

    @Test
    void When_validateNewUser_Expect_Valid() {
        User user = User.builder().firstName("testf")
                .secondName("tests")
                .login("login")
                .password("password").build();

        assertDoesNotThrow(() -> Validator.validateNewUser(user));
    }

    @Test
    void When_validateNewUserBad_Expect_Throw() {
        User user = User.builder().firstName("t")
                .secondName("t  ")
                .login("lo ")
                .password("").build();

        assertThrows(ValidationException.class, () -> Validator.validateNewUser(user));
    }
}