package ua.denitdao.servlet.shop.final_servlet_shop.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface EntityMapper<T> {

    T extractFromResultSet(ResultSet resultSet) throws SQLException;

    T makeUnique(Map<Integer, T> cache, T entity);
}
