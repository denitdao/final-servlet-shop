package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Category;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CategoryDao extends GenericDao<Category> {
    Optional<Category> findById(Long id, Locale locale);

    List<Category> findAll(Locale locale);
}
