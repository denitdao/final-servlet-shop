package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.util.Locale;
import java.util.Optional;

public interface ProductService {

    /**
     * Get product with full information
     */
    Optional<Product> getProductById(Long id, Locale locale);
}
