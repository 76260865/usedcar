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
    private Object regularImage;
    private Boolean identified;

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

    public Object getRegularImage() {
        return regularImage;
    }

    public void setRegularImage(Object regularImage) {
        this.regularImage = regularImage;
    }

    public Boolean getIdentified() {
        return identified;
    }

    public void setIdentified(Boolean identified) {
        this.identified = identified;
    }

}
