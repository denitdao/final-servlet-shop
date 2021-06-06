package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CategoryDao;
import ua.denitdao.servlet.shop.model.dao.mapper.CategoryMapper;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.util.SQLQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao {

    private static final Logger logger = LogManager.getLogger(JDBCCategoryDao.class);
    private final Connection connection;

    public JDBCCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Category entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Category> findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Category> findById(Long id, Locale locale) {
        Category category = null;

        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.CATEGORY_FIND_ID)) {
            pst.setLong(1, id);
            pst.setString(2, locale.toString());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                category = CategoryMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get category by id -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(category);
    }

    @Override
    public List<Category> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Category> findAll(Locale locale) {
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.CATEGORY_FIND_ALL)) { // open and close statement
            pst.setString(1, locale.toString());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                categories.add(CategoryMapper.getInstance().extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.warn("Failed to get all categories -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public boolean update(Category entity) {
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
