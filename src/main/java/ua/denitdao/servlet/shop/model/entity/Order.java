package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Storage for the order, containing List of it's products with amounts.
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 23958361312483534L;
    private Long id;
    private String status;
    private List<OrderProduct> products;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public static class OrderBuilder {
        private final Order entity;

        private OrderBuilder() { entity = new Order(); }

        public OrderBuilder id(Long id) {
            entity.setId(id);
            return this;
        }

        public OrderBuilder status(String status) {
            entity.setStatus(status);
            return this;
        }

        public OrderBuilder products(List<OrderProduct> products) {
            entity.setProducts(products);
            return this;
        }

        public OrderBuilder createdAt(LocalDateTime createdAt) {
            entity.setCreatedAt(createdAt);
            return this;
        }

        public OrderBuilder updatedAt(LocalDateTime updatedAt) {
            entity.setUpdatedAt(updatedAt);
            return this;
        }

        public Order build() { return entity; }
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", products=" + ((products != null) ? products.size() : "-" ) +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
