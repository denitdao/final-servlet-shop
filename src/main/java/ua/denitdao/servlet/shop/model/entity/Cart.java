package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * Temporary storage for user's chosen products.
 */
public class Cart implements Serializable {

    private Map<Product, Integer> products; // product - amount

    public Cart() {
    }

    public Cart(Map<Product, Integer> products) {
        this.products = products;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + ((products != null) ? products : "-") +
                '}';
    }
}
