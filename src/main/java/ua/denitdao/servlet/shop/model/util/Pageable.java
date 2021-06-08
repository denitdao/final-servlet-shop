package ua.denitdao.servlet.shop.model.util;

/**
 * Bean with the parameters for the requested page
 */
public class Pageable {
    public static final int PAGE_SIZE = 3;

    private Integer currentPage;
    private int pageSize;
    private Sort sort;

    public Pageable(int currentPage, int pageSize, Sort sort) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
