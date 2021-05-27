package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;

/**
 * Storage for the information about item and it's amount in the order.
 */
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 737809594172598158L;
    private Product product;
    private Integer amount;

    public OrderProduct(Product product, Integer amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "product=" + product +
                ", amount=" + amount +
                '}';
    }
}
