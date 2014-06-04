package com.jason.usedcar.model;

import java.util.Date;

public class UsedCar {

    private int productId;
    private int modelId;
    private int seriesId;
    private int brandId;
    private String[] imageIds;
    private String licenseImageId;
    private String certificateImageId;
    private Date purchaseDate;
    private double odometer;
    private double listPrice;
    private String priceType;
    private String carVin;
    private String carContact;
    private String contactPhone;
    private final int provinceId = 28;
    private final int cityId = 255;
    private int countyId;
    private String street;
    private final boolean acceptTerm = true;
    private String accessToken;
    private String deviceId;
}
