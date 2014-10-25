package com.jason.usedcar.model.data;

import java.io.Serializable;

/**
 * @author t77yq @2014-09-12.
 */
public class Product implements Serializable {

    private String productId;
    private String series;
    private String brand;
    private String productName;
    private String purchaseDate;
    private String price;
    private String odometer;
    private String regularImage;
    private String smallImage;
    private String largeImage;
    private String thumbNailImage;
    private String jumboImage;
    private String priceType;
    private int paymentMethod;
    private Boolean manufacturerVerified;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getRegularImage() {
        return regularImage;
    }

    public void setRegularImage(String regularImage) {
        this.regularImage = regularImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getThumbNailImage() {
        return thumbNailImage;
    }

    public void setThumbNailImage(String thumbNailImage) {
        this.thumbNailImage = thumbNailImage;
    }

    public String getJumboImage() {
        return jumboImage;
    }

    public void setJumboImage(String jumboImage) {
        this.jumboImage = jumboImage;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getManufacturerVerified() {
        return manufacturerVerified;
    }

    public void setManufacturerVerified(Boolean manufacturerVerified) {
        this.manufacturerVerified = manufacturerVerified;
    }

}
