package ua.denitdao.servlet.shop.model.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JDBCProductDaoTest extends DBTest {

    private ProductDao productDao;

    private final Long defCategoryId = 1L;
    private final Long defProductId = 1L;
    private final BigDecimal defPrice = BigDecimal.valueOf(200);
    private final Double defWeight = 1.5;

    @BeforeEach
    void setUp() {
        productDao = new JDBCProductDao(getConnection());
    }

    @Test
    void When_create_Expect_Persisted() {
        Product expected = Product.builder()
                .price(BigDecimal.ONE)
                .weight(1.11)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

        assertTrue(productDao.create(defCategoryId, expected));
        assertNotNull(expected.getId());
    }

    @Test
    void When_findByIdforEN_Expect_TestProductEN() {
        Product product = productDao.findById(defProductId, "en").get();

        assertEquals(defProductId, product.getId());
        assertEquals(defPrice, product.getPrice());
        assertEquals(defWeight, product.getWeight());
        assertEquals("test color", product.getColor());
    }

    @Test
    void When_findByIdforUK_Expect_TestProductUK() {
        Product product = productDao.findById(defProductId, "uk").get();

        assertEquals(defProductId, product.getId());
        assertEquals(defPrice, product.getPrice());
        assertEquals(defWeight, product.getWeight());
        assertEquals("test color uk", product.getColor());
    }

    @Test
    void When_findByIdNone_Expect_Empty() {
        assertEquals(Optional.empty(),  productDao.findById(-1L, "en"));
    }

    @AfterEach
    void tearDown() {
        productDao.close();
    }
}