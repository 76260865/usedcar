package com.jason.usedcar.model.request;

import lombok.Data;

@Data
public class PublishUsedCarRequest extends Request {

    private int productId;

    private int modelId;

    private int seriesId;

    private int brandId;

    private int[] imageIds;

    private int licenseImageId;

    private int certificateImageId;

    private String purchaseDate;

    private double odometer;

    private double listPrice;

    private String priceType;

    private String carVin;

    private String carContact;

    private String contactPhone;

    private int provinceId;

    private int cityId;

    private int countyId;

    private String street;

    private boolean acceptTerm;
}
