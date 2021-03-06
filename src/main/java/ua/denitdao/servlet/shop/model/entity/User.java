package ua.denitdao.servlet.shop.model.entity;

import ua.denitdao.servlet.shop.model.entity.enums.Roles;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 23780965332598234L;
    private Long id;
    private String firstName;
    private String secondName;
    private String login;
    private String password;
    private Roles role;
    private boolean blocked;
    private Cart cart;
    private List<Order> orders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static class UserBuilder {
        private final User entity;

        private UserBuilder() {
            entity = new User();
        }

        public UserBuilder id(Long id) {
            entity.setId(id);
            return this;
        }

        public UserBuilder firstName(String firstName) {
            entity.setFirstName(firstName);
            return this;
        }

        public UserBuilder secondName(String secondName) {
            entity.setSecondName(secondName);
            return this;
        }

        public UserBuilder login(String login) {
            entity.setLogin(login);
            return this;
        }

        public UserBuilder password(String password) {
            entity.setPassword(password);
            return this;
        }

        public UserBuilder role(Roles role) {
            entity.setRole(role);
            return this;
        }

        public UserBuilder blocked(boolean blocked) {
            entity.setBlocked(blocked);
            return this;
        }

        public UserBuilder cart(Cart cart) {
            entity.setCart(cart);
            return this;
        }

        public UserBuilder orders(List<Order> orders) {
            entity.setOrders(orders);
            return this;
        }

        public UserBuilder createdAt(LocalDateTime createdAt) {
            entity.setCreatedAt(createdAt);
            return this;
        }

        public UserBuilder updatedAt(LocalDateTime updatedAt) {
            entity.setUpdatedAt(updatedAt);
            return this;
        }

        public User build() {
            return entity;
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (blocked != user.blocked) return false;
        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(firstName, user.firstName)) return false;
        if (!Objects.equals(secondName, user.secondName)) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (role != user.role) return false;
        if (!Objects.equals(createdAt, user.createdAt)) return false;
        return Objects.equals(updatedAt, user.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", blocked=" + blocked +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
