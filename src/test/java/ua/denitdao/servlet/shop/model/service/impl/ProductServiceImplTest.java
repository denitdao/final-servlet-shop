package ua.denitdao.servlet.shop.model.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.dao.CategoryPropertyDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private Connection connection;
    @Mock
    private ProductDao productDao;
    @Mock
    private CategoryPropertyDao categoryPropertyDao;
    @Mock
    private DaoFactory daoFactory;
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void When_create_Expect_True() throws SQLException {
        Long categoryId = 1L;
        Map<String, Product> testLocalProducts = new LinkedHashMap<>();
        Product pr1 = Product.builder().id(2L).title("One lang").build();
        Product pr2 = Product.builder().title("Second lang").build();
        testLocalProducts.put("one", pr1);
        testLocalProducts.put("two", pr2);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(productDao.create(categoryId, pr1)).thenReturn(true);
        when(productDao.addLocalizedProperties(pr1, "one")).thenReturn(true);
        when(productDao.addLocalizedProperties(pr2, "two")).thenReturn(true);

        boolean result = productService.create(categoryId, testLocalProducts);

        assertTrue(result);
        assertEquals(pr1.getId(), pr2.getId());
        verify(productDao).create(eq(categoryId), any());
        verify(productDao, times(2)).addLocalizedProperties(any(), any());
        verify(connection).commit();
        verify(productDao).close();
    }

    @Test
    void When_createFail_Expect_False() {
        Long categoryId = 1L;
        Map<String, Product> testLocalProducts = new LinkedHashMap<>();
        Product pr1 = Product.builder().id(2L).title("One lang").build();
        Product pr2 = Product.builder().title("Second lang").build();
        testLocalProducts.put("one", pr1);
        testLocalProducts.put("two", pr2);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(productDao.create(categoryId, pr1)).thenReturn(false);

        boolean result = productService.create(categoryId, testLocalProducts);

        assertFalse(result);
        verify(productDao).create(eq(categoryId), any());
        verify(productDao, times(0)).addLocalizedProperties(any(), any());
        verify(productDao).close();
    }

    @Test
    void When_createAddLocalFail_Expect_False() {
        Long categoryId = 1L;
        Map<String, Product> testLocalProducts = new LinkedHashMap<>();
        Product pr1 = Product.builder().id(2L).title("One lang").build();
        Product pr2 = Product.builder().title("Second lang").build();
        testLocalProducts.put("one", pr1);
        testLocalProducts.put("two", pr2);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(productDao.create(categoryId, pr1)).thenReturn(true);
        when(productDao.addLocalizedProperties(pr1, "one")).thenReturn(false);

        boolean result = productService.create(categoryId, testLocalProducts);

        assertFalse(result);
        verify(productDao).create(categoryId, pr1);
        verify(productDao).addLocalizedProperties(any(), any());
        verify(productDao).close();
    }

    @Test
    void When_getProductById_Expect_Object() throws SQLException {
        Long id = 1L;
        String testLocale = "en";
        Optional<Product> pr1 = Optional.of(Product.builder().id(id).title("Product").build());
        Map<CategoryProperty, String> cp = new HashMap<>();
        cp.put(new CategoryProperty(1L), "value");

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(daoFactory.createCategoryPropertyDao(connection)).thenReturn(categoryPropertyDao);
        when(productDao.findById(id, testLocale)).thenReturn(pr1);
        when(categoryPropertyDao.findAllWithProductId(id, testLocale)).thenReturn(cp);

        Optional<Product> result = productService.getProductById(id, new Locale(testLocale));

        assertEquals(pr1, result);
        assertEquals(cp, result.get().getProperties());
        verify(productDao).findById(id, testLocale);
        verify(categoryPropertyDao).findAllWithProductId(id, testLocale);
        verify(connection).commit();
        verify(productDao).close();
    }

    @Test
    void When_getProductByIdFail_Expect_Empty() throws SQLException {
        Long id = 1L;
        String testLocale = "en";

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(daoFactory.createCategoryPropertyDao(connection)).thenReturn(categoryPropertyDao);
        when(productDao.findById(id, testLocale)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(id, new Locale(testLocale));

        assertEquals(Optional.empty(), result);
        verify(productDao).findById(id, testLocale);
        verify(connection).commit();
        verify(productDao).close();
    }

    @Test
    void When_getLocalizedProductById_Expect_Object() throws SQLException {
        Long id = 1L;
        String[] testLocales =  {"en", "uk"};
        Optional<Product> pr1 = Optional.of(Product.builder().id(id).title("Product en").build());
        Map<CategoryProperty, String> cp1 = new HashMap<>();
        cp1.put(new CategoryProperty(1L), "value en");
        Optional<Product> pr2 = Optional.of(Product.builder().id(id).title("Product uk").build());
        Map<CategoryProperty, String> cp2 = new HashMap<>();
        cp2.put(new CategoryProperty(1L), "value uk");
        Map<String, Product> testLocalizedProduct = new LinkedHashMap<>();
        testLocalizedProduct.put("en", pr1.get());
        testLocalizedProduct.put("uk", pr2.get());

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(daoFactory.createCategoryPropertyDao(connection)).thenReturn(categoryPropertyDao);
        when(productDao.findById(id, testLocales[0])).thenReturn(pr1);
        when(productDao.findById(id, testLocales[1])).thenReturn(pr2);
        when(categoryPropertyDao.findAllWithProductId(id, testLocales[0])).thenReturn(cp1);
        when(categoryPropertyDao.findAllWithProductId(id, testLocales[1])).thenReturn(cp2);

        Map<String, Product> result = productService.getLocalizedProductById(id, testLocales);

        assertEquals(testLocalizedProduct, result);
        assertEquals(cp1, pr1.get().getProperties());
        assertEquals(cp2, pr2.get().getProperties());
        verify(productDao, times(2)).findById(eq(id), any());
        verify(categoryPropertyDao, times(2)).findAllWithProductId(eq(id), any());
        verify(connection).commit();
        verify(productDao).close();
    }

    @Test
    void When_getLocalizedProductByIdWrong_Expect_Throw() {
        Long id = 1L;
        String[] testLocales =  {"en", "uk"};

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(daoFactory.createCategoryPropertyDao(connection)).thenReturn(categoryPropertyDao);
        when(productDao.findById(id, testLocales[0])).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getLocalizedProductById(id, testLocales));
        verify(productDao).findById(id, testLocales[0]);
        verify(productDao).close();
    }

    @Test
    void When_update_Expect_True() throws SQLException {
        Map<String, Product> testLocalProducts = new LinkedHashMap<>();
        Product pr1 = Product.builder().id(2L).title("One lang").build();
        Product pr2 = Product.builder().id(2L).title("Second lang").build();
        testLocalProducts.put("one", pr1);
        testLocalProducts.put("two", pr2);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(productDao.update(pr1)).thenReturn(true);
        when(productDao.updateLocalizedProperties(pr1, "one")).thenReturn(true);
        when(productDao.updateLocalizedProperties(pr2, "two")).thenReturn(true);

        boolean result = productService.update(testLocalProducts);

        assertTrue(result);
        verify(productDao).update(pr1);
        verify(productDao, times(2)).updateLocalizedProperties(any(), any());
        verify(connection).commit();
        verify(productDao).close();
    }

    @Test
    void When_updateFail_Expect_False() {
        Map<String, Product> testLocalProducts = new LinkedHashMap<>();
        Product pr1 = Product.builder().id(2L).title("One lang").build();
        Product pr2 = Product.builder().id(2L).title("Second lang").build();
        testLocalProducts.put("one", pr1);
        testLocalProducts.put("two", pr2);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(productDao.update(pr1)).thenReturn(false);

        boolean result = productService.update(testLocalProducts);

        assertFalse(result);
        verify(productDao).update(pr1);
        verify(productDao, times(0)).addLocalizedProperties(any(), any());
        verify(productDao).close();
    }

    @Test
    void When_delete_Expect_True() {
        Long id = 1L;

        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.delete(id)).thenReturn(true);

        boolean result = productService.delete(id);

        assertTrue(result);
        verify(productDao).delete(id);
        verify(productDao).close();
    }

    @Test
    void When_deleteThrows_Expect_False() {
        Long id = 1L;

        when(daoFactory.createProductDao()).thenReturn(productDao);
        when(productDao.delete(id)).thenThrow(new RuntimeException());

        boolean result = productService.delete(id);

        assertFalse(result);
        verify(productDao).delete(id);
        verify(productDao).close();
    }
}