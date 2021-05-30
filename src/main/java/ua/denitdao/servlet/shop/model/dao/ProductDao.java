package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {

    /**
     * Create product with basic info for the category
     */
    boolean create(Long categoryId, Product product);

    /**
     * Insert localization data for the existing product.
     */
    boolean addLocalizedProperties(Product product, String locale);

    /**
     * Get all products with basic information from specified category
     */
    List<Product> findAllWithCategoryId(Long categoryId, String locale);

    /**
     * Get product with it's basic information and some category info.
     */
    Optional<Product> findById(Long id, String locale);

    /**
     * Update localization data for the existing product.
     */
    boolean updateLocalizedProperties(Product product, String locale);
}
