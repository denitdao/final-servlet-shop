package ua.denitdao.servlet.shop.final_servlet_shop.model.entity;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 23780965332598234L;
    private int id;
    private String name;
    private String login;
    private String password;
    private String role;

    public static class UserBuilder {
        private final User entity;

        private UserBuilder() {
            entity = new User();
        }

        public UserBuilder id(int id) {
            entity.setId(id);
            return this;
        }

        public UserBuilder name(String name) {
            entity.setName(name);
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

        public UserBuilder role(String role) {
            entity.setRole(role);
            return this;
        }

        public User build() {
            return entity;
        }
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
