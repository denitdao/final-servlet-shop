package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * Temporary storage for user's chosen products.
 */
public class Cart implements Serializable {

    private Map<Long, Integer> products; // productId - amount

    public Cart() {
    }

    public Cart(Map<Long, Integer> products) {
        this.products = products;
    }

    public Map<Long, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Long, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + ((products != null) ? products : "-") +
                '}';
    }
}
