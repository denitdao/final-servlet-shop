package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    /**
     * Create and add products to the category. With all the language sets provided.
     */
    boolean create(Long categoryId, Map<String, Product> localizedProducts);

    /**
     * Get product with full information
     */
    Optional<Product> getProductById(Long id, Locale locale);

    boolean update(Map<String, Product> localizedProducts);

    Map<String, Product> getLocalizedProductById(Long id, String[] locales);

    boolean delete(Long id);
}
