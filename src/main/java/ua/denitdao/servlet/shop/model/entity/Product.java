package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Product implements Serializable {

    private static final long serialVersionUID = 43780927822592334L;
    private Long id;
    private String color;
    private BigDecimal price;
    private Double height;
    private String imageUrl;
    private Map<CategoryProperty, String> properties;
    private Category category;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public static class ProductBuilder {
        private final Product entity;

        private ProductBuilder() {
            entity = new Product();
        }

        public ProductBuilder id(Long id) {
            entity.setId(id);
            return this;
        }

        public ProductBuilder color(String color) {
            entity.setColor(color);
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            entity.setPrice(price);
            return this;
        }

        public ProductBuilder height(Double height) {
            entity.setHeight(height);
            return this;
        }

        public ProductBuilder imageUrl(String imageUrl) {
            entity.setImageUrl(imageUrl);
            return this;
        }

        public ProductBuilder properties(Map<CategoryProperty, String> properties) {
            entity.setProperties(properties);
            return this;
        }

        public ProductBuilder category(Category category) {
            entity.setCategory(category);
            return this;
        }

        public ProductBuilder createdAt(LocalDateTime createdAt) {
            entity.setCreatedAt(createdAt);
            return this;
        }

        public ProductBuilder updatedAt(LocalDateTime updatedAt) {
            entity.setUpdatedAt(updatedAt);
            return this;
        }

        public Product build() {
            return entity;
        }
    }

    public static ProductBuilder builder() { return new ProductBuilder(); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<CategoryProperty, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<CategoryProperty, String> properties) {
        this.properties = properties;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        return "Product{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", price=" + price +
                ", height=" + height +
                ", imageUrl='" + imageUrl + '\'' +
                ", properties=" + properties.size() +
                ", category=" + ((category != null) ? category.getTitle() : "-") +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
