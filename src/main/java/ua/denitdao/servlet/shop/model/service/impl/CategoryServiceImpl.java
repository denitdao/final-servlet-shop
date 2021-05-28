package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CategoryDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.service.CategoryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    /**
     * Get category with basic information and all products.
     */
    @Override
    public Optional<Category> getCategoryWithProducts(Long id, Locale locale) {
        Optional<Category> categoryOpt;

        Connection connection = daoFactory.getConnection();
        try (CategoryDao categoryDao = daoFactory.createCategoryDao(connection);
             ProductDao productDao = daoFactory.createProductDao(connection)) {
            connection.setAutoCommit(false);

            categoryOpt = categoryDao.findById(id, locale);
            Category category = categoryOpt.orElseThrow(() -> new RuntimeException("category not found"));
            category.setProducts(productDao.findAllWithCategoryId(id, locale));

            connection.commit();
        } catch (SQLException e) {
            logger.warn("transaction failed -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return categoryOpt;
    }

    /**
     * Get all categories with only basic info
     */
    @Override
    public List<Category> getAllCategories(Locale locale) {
        try (CategoryDao dao = daoFactory.createCategoryDao()) {
            return dao.findAll(locale);
        }
    }
}
