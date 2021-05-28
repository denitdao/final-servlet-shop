package ua.denitdao.servlet.shop.model.entity;

import java.io.Serializable;
import java.util.Locale;

public class CategoryProperty implements Serializable {

    private static final long serialVersionUID = 2561095618934598524L;
    private Long id;
    private Locale locale;
    private String title;
    private String dataType;

    public CategoryProperty(Long id, Locale locale, String title, String dataType) {
        this.id = id;
        this.locale = locale;
        this.title = title;
        this.dataType = dataType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "CategoryProperty{" +
                "id=" + id +
                ", locale=" + locale +
                ", title='" + title + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
