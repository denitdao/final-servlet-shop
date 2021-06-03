package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.util.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CategoryService {
    /**
     * Get category with full information.
     */
    Optional<Category> getCategoryWithProperties(Long id, Locale locale);

    /**
     * Get category with basic information and all products.
     */
    Optional<Category> getCategoryWithProducts(Long id, Locale locale, Pageable pageable);

    /**
     * Get all categories with only basic info
     */
    List<Category> getAllCategories(Locale locale);
}
