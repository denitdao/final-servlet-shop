package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;
import ua.denitdao.servlet.shop.model.util.Page;
import ua.denitdao.servlet.shop.model.util.Pageable;

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
        final String query = "insert into " +
                "products (category_id, price, weight, image_url, created_at, updated_at) " +
                "values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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
        final String query = "insert into " +
                "product_info (product_id, locale, title, description, color) " +
                "values (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
            final String inner = "insert into " +
                    "product_properties (product_id, category_properties_id, value) " +
                    "values (?, ?, ?)";
            try (PreparedStatement pst = connection.prepareStatement(inner)) {
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

        final String query = "select products.*, pi.*, c.id c_id, c.title c_title\n" +
                "from products\n" +
                "         left join product_info pi on products.id = pi.product_id\n" +
                "         left join (select id, title\n" +
                "                    from categories\n" +
                "                             left join category_info ci on categories.id = ci.category_id\n" +
                "                    where locale = ?) c\n" +
                "                   on products.category_id = c.id\n" +
                "where product_id = ?\n" +
                "  and locale = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
    public Page<Product> findAllWithCategoryId(Long categoryId, String locale, Pageable pageable,
                                               SortingOrder sortingOrder, SortingParam sortingParam, BigDecimal priceMin, BigDecimal priceMax) {
        List<Product> products = new ArrayList<>();

        final String query = "select products.*, pi.*\n" +
                "from products\n" +
                "         left join product_info pi on products.id = pi.product_id\n" +
                "where category_id = ?" +
                "  and locale = ? and deleted = 0 " +
                "  and price between ? and ? " +
                "  order by " + sortingParam.getValue() + " " + sortingOrder.getValue() +
                " limit ?,?";

        int totalProducts = countWithCategoryId(categoryId, priceMin, priceMax);
        int totalPages = (int) Math.ceil((double) totalProducts / pageable.getPageSize());
        if (pageable.getCurrentPage() > totalPages && totalPages != 0)
            pageable.setCurrentPage(totalPages);

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, categoryId);
            pst.setString(2, locale);
            pst.setBigDecimal(3, priceMin);
            pst.setBigDecimal(4, priceMax);
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
        final String query = "update products\n" +
                "set price=?, weight=?, updated_at=?, image_url=?\n" +
                " where id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
        final String query = "update product_info " +
                "set title=?, description=?, color=? " +
                "where product_id=? and locale=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
            final String inner = "update product_properties " +
                    "set value=? " +
                    "where product_id=? and category_properties_id=?";
            try (PreparedStatement pst = connection.prepareStatement(inner)) {
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
        final String query = "update products\n" +
                "set deleted=?, updated_at=?\n" +
                " where id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
        final String query = "select count(*) as total " +
                "from products p\n" +
                "         left join categories c on p.category_id = c.id\n" +
                "where category_id = ? and p.deleted=0 " +
                "  and price between ? and ? ";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
