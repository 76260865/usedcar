package com.jason.usedcar.model.data;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

public class FacetSeries {

    private int name;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getFacetSelection() {
        return facetSelection;
    }

    public void setFacetSelection(int facetSelection) {
        this.facetSelection = facetSelection;
    }

    private String count;

    private int facetSelection;

}
