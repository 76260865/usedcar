package com.jason.usedcar.model.data;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "city")
public class City extends com.activeandroid.Model {

    @Column(name = "remote_id")
    private int cityId;

    @Column(name = "province_id")
    private int provinceId;

    @Column(name = "name")
    private String cityName;

    public City() {}

    public int getCityId() {
        return cityId;
    }

    public void setCityId(final int cityId) {
        this.cityId = cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(final int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }
}
