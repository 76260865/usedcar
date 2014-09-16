package com.jason.usedcar.model.data;

public class SellingCar {

    private Integer status;
    private Integer listPrice;
    private Integer updateTime;
    private String sizeRegular;
    private String productName;
    private String productId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getListPrice() {
        return listPrice;
    }

    public void setListPrice(Integer listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getSizeRegular() {
        return sizeRegular;
    }

    public void setSizeRegular(String sizeRegular) {
        this.sizeRegular = sizeRegular;
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

}
