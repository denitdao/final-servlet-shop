package ua.denitdao.servlet.shop.model.entity.enums;

public enum SortingOrder {
    ASC("asc"),
    DESC("desc");

    private final String value;

    SortingOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
