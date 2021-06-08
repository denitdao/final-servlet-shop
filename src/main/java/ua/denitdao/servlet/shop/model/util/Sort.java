package ua.denitdao.servlet.shop.model.util;

import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;

import java.math.BigDecimal;

/**
 * Bean with configuration parameters for the products requests
 */
public class Sort {

    private SortingOrder sortingOrder;
    private SortingParam sortingParam;
    private BigDecimal priceMin;
    private BigDecimal priceMax;

    public Sort(SortingOrder sortingOrder, SortingParam sortingParam, BigDecimal priceMin, BigDecimal priceMax) {
        this.sortingOrder = sortingOrder;
        this.sortingParam = sortingParam;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public SortingParam getSortingParam() {
        return sortingParam;
    }

    public void setSortingParam(SortingParam sortingParam) {
        this.sortingParam = sortingParam;
    }

    public BigDecimal getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(BigDecimal priceMin) {
        this.priceMin = priceMin;
    }

    public BigDecimal getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(BigDecimal priceMax) {
        this.priceMax = priceMax;
    }
}
