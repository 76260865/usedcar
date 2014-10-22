package com.jason.usedcar.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

public class FacetSeries {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFacetSelection() {
        return facetSelection;
    }

    public void setFacetSelection(String facetSelection) {
        this.facetSelection = facetSelection;
    }

    private int count;

    private String facetSelection;

}
