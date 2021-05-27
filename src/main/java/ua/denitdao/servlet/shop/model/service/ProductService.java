package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Product;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductById(Long id);

}
