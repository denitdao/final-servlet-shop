package ua.denitdao.servlet.shop.model.dao.mapper;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ProductMapper implements EntityMapper<Product> {

    private static ProductMapper instance;

    private ProductMapper() {
    }

    public static synchronized ProductMapper getInstance() {
        if (instance == null)
            instance = new ProductMapper();
        return instance;
    }

    @Override
    public Product extractFromResultSet(ResultSet rs) throws SQLException {
        return Product.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .color(rs.getString("color"))
                .price(rs.getBigDecimal("price"))
                .weight(rs.getDouble("weight"))
                .imageUrl(rs.getString("image_url"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

    @Override
    public Product makeUnique(Map<Long, Product> cache, Product entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
