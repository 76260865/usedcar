package com.jason.usedcar.model.data;

import java.util.ArrayList;
import java.util.List;

public class OwnerRecord {

    private Integer ownerId;
    private String ownerPurchaseDate;
    private String locale;
    private String ownerType;
    private String holdingPeriod;
    private List<InsuranceRecord> insuranceRecords = new ArrayList<InsuranceRecord>();

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerPurchaseDate() {
        return ownerPurchaseDate;
    }

    public void setOwnerPurchaseDate(String ownerPurchaseDate) {
        this.ownerPurchaseDate = ownerPurchaseDate;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getHoldingPeriod() {
        return holdingPeriod;
    }

    public void setHoldingPeriod(String holdingPeriod) {
        this.holdingPeriod = holdingPeriod;
    }

    public List<InsuranceRecord> getInsuranceRecords() {
        return insuranceRecords;
    }

    public void setInsuranceRecords(List<InsuranceRecord> insuranceRecords) {
        this.insuranceRecords = insuranceRecords;
    }

}