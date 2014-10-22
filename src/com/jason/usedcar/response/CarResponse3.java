package com.jason.usedcar.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-10-03.
 */
public class CarResponse3 extends Response implements Serializable {

    private String productName;
    private String brandName;
    private String seriesName;
    private String modelDisplayName;
    private List<CarImage> carImages = new ArrayList<CarImage>();
    private List<String> imageIds = new ArrayList<String>();
    private List<LicenseImage> licenseImages = new ArrayList<LicenseImage>();
    private List<String> licenseImageIds = new ArrayList<String>();
    private CertificateImage certificateImage;
    private String certificateImageId;
    private String purchaseDate;
    private Integer odometer;
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
    private CarBaseInfo carBaiseInfo;
    private List<OwnerRecord> ownerRecords = new ArrayList<OwnerRecord>();


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getModelDisplayName() {
        return modelDisplayName;
    }

    public void setModelDisplayName(String modelDisplayName) {
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

    public List<LicenseImage> getLicenseImages() {
        return licenseImages;
    }

    public void setLicenseImages(List<LicenseImage> licenseImages) {
        this.licenseImages = licenseImages;
    }

    public List<String> getLicenseImageIds() {
        return licenseImageIds;
    }

    public void setLicenseImageIds(List<String> licenseImageIds) {
        this.licenseImageIds = licenseImageIds;
    }

    public CertificateImage getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(CertificateImage certificateImage) {
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

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
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

    public CarBaseInfo getCarBaiseInfo() {
        return carBaiseInfo;
    }

    public void setCarBaiseInfo(CarBaseInfo carBaiseInfo) {
        this.carBaiseInfo = carBaiseInfo;
    }

    public List<OwnerRecord> getOwnerRecords() {
        return ownerRecords;
    }

    public void setOwnerRecords(List<OwnerRecord> ownerRecords) {
        this.ownerRecords = ownerRecords;
    }

}