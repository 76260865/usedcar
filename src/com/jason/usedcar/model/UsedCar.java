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

    private String productName;

    private String productId;

    private String updateTime;

    private String status;

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
