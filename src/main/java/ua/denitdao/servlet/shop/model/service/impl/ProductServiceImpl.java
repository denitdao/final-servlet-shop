package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CategoryPropertyDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.service.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public Optional<Product> getProductById(Long id, Locale locale) {
        Connection connection = daoFactory.getConnection();
        Optional<Product> productOpt;
        try (ProductDao productDao = daoFactory.createProductDao(connection);
             CategoryPropertyDao categoryPropertyDao = daoFactory.createCategoryPropertyDao(connection)) {
            connection.setAutoCommit(false);

            productOpt = productDao.findById(id, locale);
            Product product = productOpt.orElseThrow(() -> new RuntimeException("product not found"));
            product.setProperties(categoryPropertyDao.findAllWithProductId(id, locale));

            connection.commit();
            // no autoCommit(true) because of 'enableAutoCommitOnReturn'
            // no rollbacks on exception because of 'rollbackOnReturn'
        } catch (SQLException e) {
            logger.warn("transaction failed -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return productOpt;
    }
}

/*
        MANUAL CONNECTION FAIL HANDLING

        Connection connection = daoFactory.getConnection();
        try (ProductDao productDao = daoFactory.createProductDao(connection);
             CategoryPropertyDao categoryPropertyDao = daoFactory.createCategoryPropertyDao(connection)) {
            try {
                connection.setAutoCommit(false);

                DO ACTIONS WITH DAO

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.warn("rollback. transaction failed -- {}", e.getMessage());
                throw new RuntimeException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new RuntimeException(e);
        }
*/