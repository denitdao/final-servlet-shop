package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {

    /**
     * Get all products with basic information from specified category
     */
    List<Product> findAllWithCategoryId(Long categoryId, Locale locale);

    /**
     * Get product with it's basic information and some category info.
     */
    Optional<Product> findById(Long id, Locale locale);
}
