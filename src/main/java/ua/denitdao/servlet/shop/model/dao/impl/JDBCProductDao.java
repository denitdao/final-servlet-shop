package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.dao.mapper.CategoryMapper;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class JDBCProductDao implements ProductDao {

    private static final Logger logger = LogManager.getLogger(JDBCProductDao.class);
    private final Connection connection;

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Product> findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Product> findById(Long id, Locale locale) {
        Product product = null;

        final String query = "select products.*, pi.*\n" +
                "from products\n" +
                "         left join product_info pi on products.id = pi.product_id\n" +
                "where product_id = ?" +
                "  and locale = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, id);
            pst.setString(2, locale.toString());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                product = ProductMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get category by id -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Product> findAllWithCategoryId(Long categoryId, Locale locale) {
        List<Product> products = new ArrayList<>();

        final String query = "select products.*, pi.*\n" +
                "from products\n" +
                "         left join product_info pi on products.id = pi.product_id\n" +
                "where category_id = ?" +
                "  and locale = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, categoryId);
            pst.setString(2, locale.toString());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = ProductMapper.getInstance().extractFromResultSet(rs);
                logger.info("one product {}", product);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get category by id -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public boolean update(Product entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        try {
            connection.close();
            logger.debug("Connection closed");
        } catch (SQLException e) {
            logger.warn("Failed to close connection -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
