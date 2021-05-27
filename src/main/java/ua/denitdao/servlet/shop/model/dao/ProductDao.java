package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {

    List<Product> findAllWithCategoryId(Long categoryId, Locale locale);

    Optional<Product> findById(Long id, Locale locale);
}
