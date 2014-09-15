package com.jason.usedcar.request;

/**
 * @author t77yq @2014-09-12.
 */
public class SearchProductRequest extends Request {

    private String queryString;

    private int pageSize = 20;

    private int startPage = 1;

    private String orderBy;

    private String facetSelections;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getFacetSelections() {
        return facetSelections;
    }

    public void setFacetSelections(String facetSelections) {
        this.facetSelections = facetSelections;
    }
}
