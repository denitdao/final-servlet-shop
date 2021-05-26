package ua.denitdao.servlet.shop.final_servlet_shop.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    void create(T entity);

    T findById(int id);

    List<T> findAll();

    void update(T entity);

    void delete(int id);

    @Override
    void close();
}
