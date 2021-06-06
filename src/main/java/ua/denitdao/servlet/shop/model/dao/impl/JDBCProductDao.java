package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.util.Page;
import ua.denitdao.servlet.shop.model.util.Pageable;
import ua.denitdao.servlet.shop.model.util.SQLQueries;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class JDBCProductDao implements ProductDao {

    private static final Logger logger = LogManager.getLogger(JDBCProductDao.class);
    private final Connection connection;

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product product) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Long categoryId, Product product) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            pst.setLong(1, categoryId);
            pst.setBigDecimal(2, product.getPrice());
            pst.setDouble(3, product.getWeight());
            pst.setString(4, product.getImageUrl());
            pst.setTimestamp(5, Timestamp.valueOf(product.getCreatedAt()));
            pst.setTimestamp(6, Timestamp.valueOf(product.getUpdatedAt()));

            if (pst.executeUpdate() == 0)
                return false;
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            logger.warn("Failed to create product -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addLocalizedProperties(Product product, String locale) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_INFO_INSERT)) {
            pst.setLong(1, product.getId());
            pst.setString(2, locale);
            pst.setString(3, product.getTitle());
            pst.setString(4, product.getDescription());
            pst.setString(5, product.getColor());
            if (pst.executeUpdate() == 0)
                return false;
        } catch (SQLException e) {
            logger.warn("Failed to add product basic info -- {}", e.getMessage());
            throw new RuntimeException(e);
        }

        AtomicBoolean innerQuerySuccess = new AtomicBoolean(true);
        product.getProperties().forEach((property, value) -> {
            try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_PROPERTIES_INSERT)) {
                pst.setLong(1, product.getId());
                pst.setLong(2, property.getId());
                pst.setString(3, value);

                if (pst.executeUpdate() == 0)
                    innerQuerySuccess.set(false);
            } catch (SQLException e) {
                logger.warn("Failed to create product specific info -- {}", e.getMessage());
                throw new RuntimeException(e);
            }
        });

        return innerQuerySuccess.get();
    }

    @Override
    public Optional<Product> findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Product> findById(Long id, String locale) {
        Product product = null;
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_FIND_ID)) {
            pst.setString(1, locale);
            pst.setLong(2, id);
            pst.setString(3, locale);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                product = ProductMapper.getInstance().extractFromResultSet(rs);
                product.setCategory(Category.builder()
                        .id(rs.getLong("c_id"))
                        .title(rs.getString("c_title")).build());
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
    public Page<Product> findAllWithCategoryId(Long categoryId, String locale, Pageable pageable) {
        List<Product> products = new ArrayList<>();

        int totalProducts = countWithCategoryId(categoryId, pageable.getSort().getPriceMin(), pageable.getSort().getPriceMax());
        int totalPages = (int) Math.ceil((double) totalProducts / pageable.getPageSize());
        if (pageable.getCurrentPage() > totalPages && totalPages != 0)
            pageable.setCurrentPage(totalPages);

        String query = SQLQueries.PRODUCT_FIND_ALL_CATEGORY +
                " order by " + pageable.getSort().getSortingParam().getValue() +
                " " + pageable.getSort().getSortingOrder().getValue() +
                SQLQueries.PRODUCT_FIND_ALL_CATEGORY_LIMIT;
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, categoryId);
            pst.setString(2, locale);
            pst.setBigDecimal(3, pageable.getSort().getPriceMin());
            pst.setBigDecimal(4, pageable.getSort().getPriceMax());
            pst.setInt(5, (pageable.getCurrentPage() - 1) * pageable.getPageSize());
            pst.setInt(6, pageable.getCurrentPage() * pageable.getPageSize());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Product product = ProductMapper.getInstance().extractFromResultSet(rs);
                products.add(product);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get products page from category -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return new Page<>(totalPages, products);
    }

    @Override
    public boolean update(Product product) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_UPDATE)) {
            pst.setBigDecimal(1, product.getPrice());
            pst.setDouble(2, product.getWeight());
            pst.setTimestamp(3, Timestamp.valueOf(product.getUpdatedAt()));
            pst.setString(4, product.getImageUrl());
            pst.setLong(5, product.getId());

            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to update product -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateLocalizedProperties(Product product, String locale) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_INFO_UPDATE)) {
            pst.setString(1, product.getTitle());
            pst.setString(2, product.getDescription());
            pst.setString(3, product.getColor());
            pst.setLong(4, product.getId());
            pst.setString(5, locale);
            if (pst.executeUpdate() == 0)
                return false;
        } catch (SQLException e) {
            logger.warn("Failed to update product basic info -- {}", e.getMessage());
            throw new RuntimeException(e);
        }

        AtomicBoolean innerQuerySuccess = new AtomicBoolean(true);
        product.getProperties().forEach((property, value) -> {
            try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_PROPERTIES_UPDATE)) {
                pst.setString(1, value);
                pst.setLong(2, product.getId());
                pst.setLong(3, property.getId());

                if (pst.executeUpdate() == 0)
                    innerQuerySuccess.set(false);
            } catch (SQLException e) {
                logger.warn("Failed to update product specific info -- {}", e.getMessage());
                throw new RuntimeException(e);
            }
        });

        return innerQuerySuccess.get();
    }

    @Override
    public boolean delete(Long id) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_DELETE)) {
            pst.setBoolean(1, true);
            pst.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pst.setLong(3, id);

            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to delete product -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private int countWithCategoryId(Long categoryId, BigDecimal priceMin, BigDecimal priceMax) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCT_COUNT_CATEGORY)) {
            pst.setLong(1, categoryId);
            pst.setBigDecimal(2, priceMin);
            pst.setBigDecimal(3, priceMax);

            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt("total");
        } catch (SQLException e) {
            logger.warn("Failed to count products in category -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
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
