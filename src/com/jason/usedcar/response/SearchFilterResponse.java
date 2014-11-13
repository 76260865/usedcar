package com.jason.usedcar.response;

import com.jason.usedcar.model.data.BrandFilterEntity;
import com.jason.usedcar.model.data.FilterEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-11-09.
 */
public class SearchFilterResponse extends Response {

    private List<BrandFilterEntity> brand = new ArrayList<BrandFilterEntity>();
    private List<FilterEntity> odometer = new ArrayList<FilterEntity>();
    private List<FilterEntity> price = new ArrayList<FilterEntity>();
    private List<FilterEntity> manufacturerVerified = new ArrayList<FilterEntity>();
    private List<FilterEntity> age = new ArrayList<FilterEntity>();

    public List<BrandFilterEntity> getBrand() {
        return brand;
    }

    public void setBrand(List<BrandFilterEntity> brand) {
        this.brand = brand;
    }

    public List<FilterEntity> getOdometer() {
        return odometer;
    }

    public void setOdometer(List<FilterEntity> odometer) {
        this.odometer = odometer;
    }

    public List<FilterEntity> getPrice() {
        return price;
    }

    public void setPrice(List<FilterEntity> price) {
        this.price = price;
    }

    public List<FilterEntity> getManufacturerVerified() {
        return manufacturerVerified;
    }

    public void setManufacturerVerified(List<FilterEntity> manufacturerVerified) {
        this.manufacturerVerified = manufacturerVerified;
    }

    public List<FilterEntity> getAge() {
        return age;
    }

    public void setAge(List<FilterEntity> age) {
        this.age = age;
    }
}