package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Category;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> getCategoryWithProperties(Long id, Locale locale);

    Optional<Category> getCategoryWithProducts(Long id, Locale locale);

    List<Category> getAllCategories(Locale locale);
}
