package ua.denitdao.servlet.shop.model.dao.mapper;

import ua.denitdao.servlet.shop.model.entity.CategoryProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CategoryPropertyMapper implements EntityMapper<CategoryProperty> {

    private static CategoryPropertyMapper instance;

    private CategoryPropertyMapper() {
    }

    public static synchronized CategoryPropertyMapper getInstance() {
        if (instance == null)
            instance = new CategoryPropertyMapper();
        return instance;
    }

    @Override
    public CategoryProperty extractFromResultSet(ResultSet rs) throws SQLException {
        return new CategoryProperty(rs.getLong("id"), null,
                rs.getString("title"), rs.getString("data_type"));
    }

    @Override
    public CategoryProperty makeUnique(Map<Long, CategoryProperty> cache, CategoryProperty entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
