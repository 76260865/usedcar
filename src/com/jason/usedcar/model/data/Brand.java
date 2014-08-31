package com.jason.usedcar.model.data;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "brand")
public class Brand extends com.activeandroid.Model{

    @Column(name = "remote_id")
    private int brandId;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "alpha")
    private String index;

    @Column(name = "name")
    private String name;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(final String brandName) {
        this.brandName = brandName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(final String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
