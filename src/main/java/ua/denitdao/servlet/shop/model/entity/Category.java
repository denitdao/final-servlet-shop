package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Storage of the unique localized properties and products.
 */
public class Category implements Serializable {

    private static final long serialVersionUID = 237809561332598534L;
    private Long id;
    private String title;
    private String description;
    private List<CategoryProperty> categoryProperties;
    private List<Product> products;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public static class CategoryBuilder {
        private final Category entity;

        private CategoryBuilder() {
            entity = new Category();
        }

        public CategoryBuilder id(Long id) {
            entity.setId(id);
            return this;
        }

        public CategoryBuilder title(String title) {
            entity.setTitle(title);
            return this;
        }

        public CategoryBuilder description(String description) {
            entity.setDescription(description);
            return this;
        }

        public CategoryBuilder categoryProperties(List<CategoryProperty> categoryProperties) {
            entity.setCategoryProperties(categoryProperties);
            return this;
        }

        public CategoryBuilder products(List<Product> products) {
            entity.setProducts(products);
            return this;
        }

        public CategoryBuilder updatedAt(LocalDateTime updatedAt) {
            entity.setUpdatedAt(updatedAt);
            return this;
        }

        public CategoryBuilder createdAt(LocalDateTime createdAt) {
            entity.setCreatedAt(createdAt);
            return this;
        }

        public Category build() {
            return entity;
        }
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryProperty> getCategoryProperties() {
        return categoryProperties;
    }

    public void setCategoryProperties(List<CategoryProperty> categoryProperties) {
        this.categoryProperties = categoryProperties;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
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
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", products=" + products +
                ", categoryProperties=" + categoryProperties +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
