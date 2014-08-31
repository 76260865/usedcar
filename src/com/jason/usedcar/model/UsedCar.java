package com.jason.usedcar.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-08-02.
 */
public class UsedCar {

    private List<String> imageUrls = new ArrayList<String>();

    private String purchaseDate;

    private float odometer;

    private float listPrice;

    private String priceType;

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public UsedCar setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public UsedCar setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public float getOdometer() {
        return odometer;
    }

    public UsedCar setOdometer(int odometer) {
        this.odometer = odometer;
        return this;
    }

    public float getListPrice() {
        return listPrice;
    }

    public UsedCar setListPrice(int listPrice) {
        this.listPrice = listPrice;
        return this;
    }

    public String getPriceType() {
        return priceType;
    }

    public UsedCar setPriceType(String priceType) {
        this.priceType = priceType;
        return this;
    }
}
