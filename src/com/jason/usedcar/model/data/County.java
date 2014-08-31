package com.jason.usedcar.model.data;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "County")
public class County extends com.activeandroid.Model{

    @Column(name = "remote_id")
    private int countyId;

    @Column(name = "city_id")
    private int cityId;

    @Column(name = "name")
    private String countyName;

    public County() {}

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(final int countyId) {
        this.countyId = countyId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(final int cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(final String countyName) {
        this.countyName = countyName;
    }
}
