package ua.denitdao.servlet.shop.model.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.dao.CategoryDao;
import ua.denitdao.servlet.shop.model.dao.CategoryPropertyDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;
import ua.denitdao.servlet.shop.model.util.Page;
import ua.denitdao.servlet.shop.model.util.Pageable;
import ua.denitdao.servlet.shop.model.util.Sort;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private Connection connection;
    @Mock
    DaoFactory daoFactory;
    @Mock
    CategoryDao categoryDao;
    @Mock
    ProductDao productDao;
    @Mock
    CategoryPropertyDao categoryPropertyDao;
    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void When_getCategoryWithProperties_Expect_Object() throws SQLException {
        Long id = 1L;
        Locale testLocale = new Locale("en");
        List<CategoryProperty> testCategoryProperties =
                Arrays.asList(new CategoryProperty(1L, new Locale("uk"), "title1", "num"),
                        new CategoryProperty(2L, new Locale("uk"), "title2", "string"));
        Optional<Category> expected = Optional.of(Category.builder().title("someCategory").build());

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createCategoryDao(connection)).thenReturn(categoryDao);
        when(daoFactory.createCategoryPropertyDao(connection)).thenReturn(categoryPropertyDao);
        when(categoryDao.findById(id, testLocale)).thenReturn(expected);
        when(categoryPropertyDao.findAllWithCategoryId(id)).thenReturn(testCategoryProperties);

        Optional<Category> result = categoryService.getCategoryWithProperties(id, testLocale);

        assertEquals(expected, result);
        verify(categoryDao).findById(id, testLocale);
        verify(categoryPropertyDao).findAllWithCategoryId(id);
        verify(connection).commit();
        verify(categoryDao).close();
    }

    @Test
    void When_getCategoryWithPropertiesNone_Expect_Empty() throws SQLException {
        Long id = 1L;
        Locale testLocale = new Locale("en");

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createCategoryDao(connection)).thenReturn(categoryDao);
        when(daoFactory.createCategoryPropertyDao(connection)).thenReturn(categoryPropertyDao);
        when(categoryDao.findById(id, testLocale)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryWithProperties(id, testLocale);

        assertEquals(Optional.empty(), result);
        verify(categoryDao).findById(id, testLocale);
        verify(categoryPropertyDao, times(0)).findAllWithCategoryId(id);
        verify(connection).commit();
        verify(categoryDao).close();
    }

    @Test
    void When_getCategoryWithProducts_Expect_Object() throws SQLException {
        Long categoryId = 1L;
        Locale testLocale = new Locale("en");
        Pageable testPageable = new Pageable(1, 1,
                new Sort(SortingOrder.ASC, SortingParam.DATE, BigDecimal.ZERO, BigDecimal.TEN));
        Page<Product> testProducts = new Page<>(2,
                        Arrays.asList(Product.builder().id(1L).build(), Product.builder().id(2L).build()));
        Optional<Category> expected = Optional.of(Category.builder().title("someCategory").build());

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createCategoryDao(connection)).thenReturn(categoryDao);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(categoryDao.findById(categoryId, testLocale)).thenReturn(expected);
        when(productDao.findAllWithCategoryId(categoryId, testLocale.toString(), testPageable)).thenReturn(testProducts);

        Optional<Category> result = categoryService.getCategoryWithProducts(categoryId, testLocale, testPageable);

        assertEquals(expected, result);
        assertEquals(testProducts, expected.get().getProducts());
        verify(categoryDao).findById(categoryId, testLocale);
        verify(productDao).findAllWithCategoryId(categoryId, testLocale.toString(), testPageable);
        verify(connection).commit();
        verify(categoryDao).close();
    }

    @Test
    void When_getCategoryWithProductsFail_Expect_Empty() throws SQLException {
        Long categoryId = 1L;
        Locale testLocale = new Locale("en");
        Pageable testPageable = new Pageable(1, 1,
                new Sort(SortingOrder.ASC, SortingParam.DATE, BigDecimal.ZERO, BigDecimal.TEN));

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createCategoryDao(connection)).thenReturn(categoryDao);
        when(daoFactory.createProductDao(connection)).thenReturn(productDao);
        when(categoryDao.findById(categoryId, testLocale)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryWithProducts(categoryId, testLocale, testPageable);

        assertEquals(Optional.empty(), result);
        verify(categoryDao).findById(categoryId, testLocale);
        verify(connection).commit();
        verify(categoryDao).close();
    }

    @Test
    void When_getAllCategories_Expect_List() {
        Locale testLocale = new Locale("en");
        List<Category> expected = Arrays.asList(Category.builder().id(1L).build(),
                Category.builder().id(1L).build());

        when(daoFactory.createCategoryDao()).thenReturn(categoryDao);
        when(categoryDao.findAll(testLocale)).thenReturn(expected);

        List<Category> result = categoryService.getAllCategories(testLocale);

        assertEquals(expected, result);
        verify(categoryDao).findAll(testLocale);
        verify(categoryDao).close();
    }
}