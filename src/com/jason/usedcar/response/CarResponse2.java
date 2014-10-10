package com.jason.usedcar.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-09-29.
 */

public class CarResponse2 extends Response {

    private String productName;
    private Object brandName;
    private Object seriesName;
    private Object modelDisplayName;
    private List<CarImage> carImages = new ArrayList<CarImage>();
    private List<String> imageIds = new ArrayList<String>();
    private List<Object> licenseImages = new ArrayList<Object>();
    private List<Object> licenseImageIds = new ArrayList<Object>();
    private Object certificateImage;
    private String certificateImageId;
    private String purchaseDate;
    private Float odometer;
    private String listPrice;
    private Integer priceType;
    private Integer paymentMethod;
    private String carVin;
    private String carContact;
    private String contactPhone;
    private Integer status;
    private String updateTime;
    private String province;
    private String city;
    private String county;
    private String street;
    private Object carBasicInfo;
    private List<Object> ownerRecords = new ArrayList<Object>();

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Object getBrandName() {
        return brandName;
    }

    public void setBrandName(Object brandName) {
        this.brandName = brandName;
    }

    public Object getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(Object seriesName) {
        this.seriesName = seriesName;
    }

    public Object getModelDisplayName() {
        return modelDisplayName;
    }

    public void setModelDisplayName(Object modelDisplayName) {
        this.modelDisplayName = modelDisplayName;
    }

    public List<CarImage> getCarImages() {
        return carImages;
    }

    public void setCarImages(List<CarImage> carImages) {
        this.carImages = carImages;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public List<Object> getLicenseImages() {
        return licenseImages;
    }

    public void setLicenseImages(List<Object> licenseImages) {
        this.licenseImages = licenseImages;
    }

    public List<Object> getLicenseImageIds() {
        return licenseImageIds;
    }

    public void setLicenseImageIds(List<Object> licenseImageIds) {
        this.licenseImageIds = licenseImageIds;
    }

    public Object getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(Object certificateImage) {
        this.certificateImage = certificateImage;
    }

    public String getCertificateImageId() {
        return certificateImageId;
    }

    public void setCertificateImageId(String certificateImageId) {
        this.certificateImageId = certificateImageId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Float getOdometer() {
        return odometer;
    }

    public void setOdometer(Float odometer) {
        this.odometer = odometer;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public String getCarContact() {
        return carContact;
    }

    public void setCarContact(String carContact) {
        this.carContact = carContact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Object getCarBasicInfo() {
        return carBasicInfo;
    }

    public void setCarBasicInfo(Object carBasicInfo) {
        this.carBasicInfo = carBasicInfo;
    }

    public List<Object> getOwnerRecords() {
        return ownerRecords;
    }

    public void setOwnerRecords(List<Object> ownerRecords) {
        this.ownerRecords = ownerRecords;
    }

}
