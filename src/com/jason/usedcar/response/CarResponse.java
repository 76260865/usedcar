package com.jason.usedcar.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-10-03.
 */
public class CarResponse extends Response implements Serializable {

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
    private double odometer;
    private double listPrice;
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
    private String carBasicInfoUrl;
    private String productId;
    private int brandId;
    private int seriesId;
    private int modelId;
    private int provinceId;
    private int cityId;
    private int countyId;
    private boolean inCart;
    private boolean inFavoriteList;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountyId() {
        return countyId;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public boolean isInFavoriteList() {
        return inFavoriteList;
    }

    public void setInFavoriteList(boolean inFavoriteList) {
        this.inFavoriteList = inFavoriteList;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

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

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
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

    public String getCarBasicInfoUrl() {
        return carBasicInfoUrl;
    }

    public void setCarBasicInfoUrl(String carBasicInfoUrl) {
        this.carBasicInfoUrl = carBasicInfoUrl;
    }
}