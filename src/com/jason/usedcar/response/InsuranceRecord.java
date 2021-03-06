package com.jason.usedcar.response;

import java.io.Serializable;

/**
 * @author t77yq @2014-10-03.
 */
public class InsuranceRecord implements Serializable {

    private Integer insuranceId;
    private String insuranceDate;
    private String company;
    private String amount;

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        this.insuranceDate = insuranceDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
