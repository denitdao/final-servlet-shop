package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;
import ua.denitdao.servlet.shop.model.util.Page;
import ua.denitdao.servlet.shop.model.util.Pageable;

import java.math.BigDecimal;
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
    Page<Product> findAllWithCategoryId(Long categoryId, String locale, Pageable pageable, SortingOrder sortingOrder, SortingParam sortingParam, BigDecimal priceMin, BigDecimal priceMax);

    /**
     * Get product with it's basic information and some category info.
     */
    Optional<Product> findById(Long id, String locale);

    /**
     * Update localization data for the existing product.
     */
    boolean updateLocalizedProperties(Product product, String locale);
}
