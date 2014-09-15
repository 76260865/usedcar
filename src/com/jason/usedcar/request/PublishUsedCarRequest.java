package com.jason.usedcar.request;

public class PublishUsedCarRequest extends Request {
    private Integer modelId;
    private Integer seriesId;
    private Integer brandId;
    private String imageIds;
    private String licenseImageIds;
    private String certificateImageId;
    private String purchaseDate;
    private Double odometer;
    private Double listPrice;
    private Integer priceType;
    private Integer paymentMethod;
    private String carVin;
    private String carContact;
    private String contactPhone;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private String street;
    private Boolean acceptTerm;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(final Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(final Integer seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(final Integer brandId) {
        this.brandId = brandId;
    }

    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(final String imageIds) {
        this.imageIds = imageIds;
    }

    public String getLicenseImageIds() {
        return licenseImageIds;
    }

    public void setLicenseImageIds(final String licenseImageIds) {
        this.licenseImageIds = licenseImageIds;
    }

    public String getCertificateImageId() {
        return certificateImageId;
    }

    public void setCertificateImageId(final String certificateImageId) {
        this.certificateImageId = certificateImageId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(final String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(final Double odometer) {
        this.odometer = odometer;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(final Double listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(final Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(final String carVin) {
        this.carVin = carVin;
    }

    public String getCarContact() {
        return carContact;
    }

    public void setCarContact(final String carContact) {
        this.carContact = carContact;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(final String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(final Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(final Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(final Integer countyId) {
        this.countyId = countyId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public Boolean getAcceptTerm() {
        return acceptTerm;
    }

    public void setAcceptTerm(final Boolean acceptTerm) {
        this.acceptTerm = acceptTerm;
    }
}
