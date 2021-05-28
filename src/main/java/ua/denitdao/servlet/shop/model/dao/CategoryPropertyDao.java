package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.CategoryProperty;

import java.util.Locale;
import java.util.Map;

public interface CategoryPropertyDao extends GenericDao<CategoryProperty> {

    /**
     * Get all of the properties with values for the product
     */
    Map<CategoryProperty, String> findAllWithProductId(Long propertyId, Locale locale);
}
