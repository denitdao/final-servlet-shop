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
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean create(Long categoryId, Map<String, Product> localizedProducts) {
        Connection connection = daoFactory.getConnection();
        try (ProductDao dao = daoFactory.createProductDao(connection)) {
            connection.setAutoCommit(false);

            AtomicBoolean inserted = new AtomicBoolean(false);
            AtomicLong insertedProductId = new AtomicLong(-1L);
            localizedProducts.forEach((locale, product) -> {
                if (!inserted.get()) {
                    product.setCreatedAt(LocalDateTime.now());
                    product.setUpdatedAt(LocalDateTime.now());
                    inserted.set(dao.create(categoryId, product));
                    insertedProductId.set(product.getId());
                }
                product.setId(insertedProductId.get());
                if (!dao.addLocalizedProperties(product, locale))
                    throw new RuntimeException("Failed to add localized properties");
            });

            if (inserted.get()) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (RuntimeException | SQLException e) {
            logger.warn("product create transaction failed -- {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Product> getProductById(Long id, Locale locale) {
        Connection connection = daoFactory.getConnection();
        Optional<Product> productOpt;
        try (ProductDao productDao = daoFactory.createProductDao(connection);
             CategoryPropertyDao categoryPropertyDao = daoFactory.createCategoryPropertyDao(connection)) {
            connection.setAutoCommit(false);

            productOpt = productDao.findById(id, locale.toString());
            productOpt.ifPresent(p ->
                    p.setProperties(categoryPropertyDao.findAllWithProductId(id, locale.toString()))
            );

            connection.commit();
            // no autoCommit(true) because of 'enableAutoCommitOnReturn'
            // no rollbacks on exception because of 'rollbackOnReturn'
        } catch (SQLException e) {
            logger.warn("get product transaction failed -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return productOpt;
    }

    @Override
    public Map<String, Product> getLocalizedProductById(Long id, String[] locales) {
        Connection connection = daoFactory.getConnection();
        Map<String, Product> localizedProduct = new LinkedHashMap<>();
        try (ProductDao productDao = daoFactory.createProductDao(connection);
             CategoryPropertyDao categoryPropertyDao = daoFactory.createCategoryPropertyDao(connection)) {
            connection.setAutoCommit(false);

            for (String locale : locales) {
                Optional<Product> productOpt = productDao.findById(id, locale);
                Product product = productOpt.orElseThrow(() -> new RuntimeException("product not found"));
                product.setProperties(categoryPropertyDao.findAllWithProductId(id, locale));
                localizedProduct.put(locale, product);
            }

            connection.commit();
        } catch (SQLException e) {
            logger.warn("get product transaction failed -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return localizedProduct;
    }

    @Override
    public boolean update(Map<String, Product> localizedProducts) {
        Connection connection = daoFactory.getConnection();
        try (ProductDao dao = daoFactory.createProductDao(connection)) {
            connection.setAutoCommit(false);

            AtomicBoolean updated = new AtomicBoolean(false);
            localizedProducts.forEach((locale, product) -> {
                if (!updated.get()) {
                    product.setUpdatedAt(LocalDateTime.now());
                    updated.set(dao.update(product));
                }
                if (!dao.updateLocalizedProperties(product, locale) || !updated.get())
                    throw new RuntimeException("Failed to update localized properties");
            });

            connection.commit();
            return true;
        } catch (RuntimeException | SQLException e) {
            logger.warn("product update transaction failed -- {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        Connection connection = daoFactory.getConnection();
        try (ProductDao dao = daoFactory.createProductDao(connection)) {
            return dao.delete(id);
        } catch (RuntimeException e) {
            return false;
        }
    }
}