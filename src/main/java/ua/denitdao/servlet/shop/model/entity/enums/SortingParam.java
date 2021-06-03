package ua.denitdao.servlet.shop.model.entity.enums;

public enum SortingParam {
    TITLE("title"),
    PRICE("price"),
    DATE("created_at");

    private final String value;

    SortingParam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
