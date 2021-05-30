package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CategoryPropertyDao;
import ua.denitdao.servlet.shop.model.dao.mapper.CategoryPropertyMapper;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.entity.CategoryProperty;
import ua.denitdao.servlet.shop.model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JDBCCategoryPropertyDao implements CategoryPropertyDao {

    private static final Logger logger = LogManager.getLogger(JDBCCategoryPropertyDao.class);
    private final Connection connection;

    public JDBCCategoryPropertyDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CategoryProperty> findAllWithCategoryId(Long categoryId) {
        List<CategoryProperty> categoryProperties = new ArrayList<>();

        final String query = "select *\n" +
                "from category_properties\n" +
                "where category_id = ? order by locale";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, categoryId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CategoryProperty categoryProperty = CategoryPropertyMapper.getInstance().extractFromResultSet(rs);
                categoryProperty.setLocale(new Locale(rs.getString("locale")));
                categoryProperties.add(categoryProperty);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get properties of the category -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return categoryProperties;
    }

    @Override
    public Map<CategoryProperty, String> findAllWithProductId(Long productId, String locale) {
        Map<CategoryProperty, String> properties = new LinkedHashMap<>();

        final String query = "select cp.id, title, value, data_type\n" +
                "from category_properties cp\n" +
                "         left join product_properties pp on cp.id = pp.category_properties_id\n" +
                "where product_id = ?\n" +
                "  and locale = ? order by locale";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, productId);
            pst.setString(2, locale);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                CategoryProperty categoryProperty = CategoryPropertyMapper.getInstance().extractFromResultSet(rs);
                properties.put(categoryProperty, rs.getString("value"));
            }
        } catch (SQLException e) {
            logger.warn("Failed to get properties of the product -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Override
    public boolean create(CategoryProperty entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<CategoryProperty> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CategoryProperty> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(CategoryProperty entity) {
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
