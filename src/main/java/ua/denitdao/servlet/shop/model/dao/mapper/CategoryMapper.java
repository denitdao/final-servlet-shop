package ua.denitdao.servlet.shop.model.dao.mapper;

import ua.denitdao.servlet.shop.model.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CategoryMapper implements EntityMapper<Category> {
    private static CategoryMapper instance;

    private CategoryMapper() {
    }

    public static synchronized CategoryMapper getInstance() {
        if (instance == null)
            instance = new CategoryMapper();
        return instance;
    }

    @Override
    public Category extractFromResultSet(ResultSet rs) throws SQLException {
        return Category.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

    @Override
    public Category makeUnique(Map<Long, Category> cache, Category entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
