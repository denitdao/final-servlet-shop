package ua.denitdao.servlet.shop.model.dao.mapper;

import ua.denitdao.servlet.shop.model.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OrderMapper implements EntityMapper<Order> {
    private static OrderMapper instance;

    private OrderMapper() {
    }

    public static synchronized OrderMapper getInstance() {
        if (instance == null)
            instance = new OrderMapper();
        return instance;
    }

    @Override
    public Order extractFromResultSet(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getLong("id"))
                .status(rs.getString("status"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

    @Override
    public Order makeUnique(Map<Long, Order> cache, Order entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
