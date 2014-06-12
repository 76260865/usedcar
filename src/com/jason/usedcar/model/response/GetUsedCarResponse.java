package com.jason.usedcar.model.response;

import lombok.Data;

@Data
public class GetUsedCarResponse extends Response {

    private String productName;

    private String brandName;

    private String seriesName;

    private String modelDisplayName;

    private String[] imageUrls;

    private String licenseImage;

    private String certificateImage;

    private String purchaseDate;

    private int odometer;

    private int listPrice;

    private String priceType;

    private String carVin;

    private String carContact;

    private String contactPhone;

    private int status;

    private String updateTime;

    private String province;

    private String city;

    private String county;

    private String street;
}
