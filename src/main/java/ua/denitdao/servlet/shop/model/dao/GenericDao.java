package ua.denitdao.servlet.shop.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {

    boolean create(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(Long id);

    @Override
    void close();
}
