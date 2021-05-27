package ua.denitdao.servlet.shop.model.dao.mapper;

import ua.denitdao.servlet.shop.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements EntityMapper<User> {
    private static UserMapper instance;

    private UserMapper() {
    }

    public static synchronized UserMapper getInstance() {
        if (instance == null)
            instance = new UserMapper();
        return instance;
    }

    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .firstName(rs.getString("first_name"))
                .secondName(rs.getString("second_name"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .role(rs.getString("role"))
//                .blocked(rs.getBoolean("blocked"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }

    @Override
    public User makeUnique(Map<Long, User> cache, User entity) {
        cache.putIfAbsent(entity.getId(), entity);
        return cache.get(entity.getId());
    }
}
