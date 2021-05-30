package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.CategoryProperty;

import java.util.List;
import java.util.Map;

public interface CategoryPropertyDao extends GenericDao<CategoryProperty> {

    /**
     * Find all property names for the category.
     */
    List<CategoryProperty> findAllWithCategoryId(Long categoryId);

    /**
     * Get all of the properties with values for the product
     */
    Map<CategoryProperty, String> findAllWithProductId(Long propertyId, String locale);
}
