package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Category;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CategoryDao extends GenericDao<Category> {

    /**
     * Get category with basic information
     */
    Optional<Category> findById(Long id, Locale locale);

    /**
     * Get all categories with their basic information
     */
    List<Category> findAll(Locale locale);
}
