package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */

public class SeriesRequest extends Request {

    private int brandId;

    private String facetSelections;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(final int brandId) {
        this.brandId = brandId;
    }

    public String getFacetSelections() {
        return facetSelections;
    }

    public void setFacetSelections(String facetSelections) {
        this.facetSelections = facetSelections;
    }
}
