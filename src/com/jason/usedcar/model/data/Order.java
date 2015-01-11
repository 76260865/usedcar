package com.jason.usedcar.model.data;

import java.io.Serializable;

/**
 * @author t77yq @2014-11-01.
 */
public class Order implements Serializable {

    private String orderId;
    private String submittedDate;
    private String retailerName;
    private String retailerIdInfo;
    private String retailerIdType;
    private String customerName;
    private String customerIdInfo;
    private String customerIdType;
    private String productImage;
    private String productName;
    private String productCarVin;
    private String productId;
    private Boolean manufacturerVerified;
    private Float subtotal;
    private Float customerServiceFee;
    private Float retailerServiceFee;
    private String grantedLoanAmount;
    private String onlinePayedAmount;
    private String retailerReceived;
    private String orderState;
    private Integer paymentType;

    /**
     *
     * @return
     * The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     * The submittedDate
     */
    public String getSubmittedDate() {
        return submittedDate;
    }

    /**
     *
     * @param submittedDate
     * The submittedDate
     */
    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    /**
     *
     * @return
     * The retailerName
     */
    public String getRetailerName() {
        return retailerName;
    }

    /**
     *
     * @param retailerName
     * The retailerName
     */
    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    /**
     *
     * @return
     * The retailerIdInfo
     */
    public String getRetailerIdInfo() {
        return retailerIdInfo;
    }

    /**
     *
     * @param retailerIdInfo
     * The retailerIdInfo
     */
    public void setRetailerIdInfo(String retailerIdInfo) {
        this.retailerIdInfo = retailerIdInfo;
    }

    /**
     *
     * @return
     * The retailerIdType
     */
    public String getRetailerIdType() {
        return retailerIdType;
    }

    /**
     *
     * @param retailerIdType
     * The retailerIdType
     */
    public void setRetailerIdType(String retailerIdType) {
        this.retailerIdType = retailerIdType;
    }

    /**
     *
     * @return
     * The customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @param customerName
     * The customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     *
     * @return
     * The customerIdInfo
     */
    public String getCustomerIdInfo() {
        return customerIdInfo;
    }

    /**
     *
     * @param customerIdInfo
     * The customerIdInfo
     */
    public void setCustomerIdInfo(String customerIdInfo) {
        this.customerIdInfo = customerIdInfo;
    }

    /**
     *
     * @return
     * The customerIdType
     */
    public String getCustomerIdType() {
        return customerIdType;
    }

    /**
     *
     * @param customerIdType
     * The customerIdType
     */
    public void setCustomerIdType(String customerIdType) {
        this.customerIdType = customerIdType;
    }

    /**
     *
     * @return
     * The productImage
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     *
     * @param productImage
     * The productImage
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     * The productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     * The productCarVin
     */
    public String getProductCarVin() {
        return productCarVin;
    }

    /**
     *
     * @param productCarVin
     * The productCarVin
     */
    public void setProductCarVin(String productCarVin) {
        this.productCarVin = productCarVin;
    }

    /**
     *
     * @return
     * The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     * The productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     * The manufacturerVerified
     */
    public Boolean getManufacturerVerified() {
        return manufacturerVerified;
    }

    /**
     *
     * @param manufacturerVerified
     * The manufacturerVerified
     */
    public void setManufacturerVerified(Boolean manufacturerVerified) {
        this.manufacturerVerified = manufacturerVerified;
    }

    /**
     *
     * @return
     * The subtotal
     */
    public Float getSubtotal() {
        return subtotal;
    }

    /**
     *
     * @param subtotal
     * The subtotal
     */
    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    /**
     *
     * @return
     * The customerServiceFee
     */
    public Float getCustomerServiceFee() {
        return customerServiceFee;
    }

    /**
     *
     * @param customerServiceFee
     * The customerServiceFee
     */
    public void setCustomerServiceFee(Float customerServiceFee) {
        this.customerServiceFee = customerServiceFee;
    }

    /**
     *
     * @return
     * The retailerServiceFee
     */
    public Float getRetailerServiceFee() {
        return retailerServiceFee;
    }

    /**
     *
     * @param retailerServiceFee
     * The retailerServiceFee
     */
    public void setRetailerServiceFee(Float retailerServiceFee) {
        this.retailerServiceFee = retailerServiceFee;
    }

    /**
     *
     * @return
     * The grantedLoanAmount
     */
    public String getGrantedLoanAmount() {
        return grantedLoanAmount;
    }

    /**
     *
     * @param grantedLoanAmount
     * The grantedLoanAmount
     */
    public void setGrantedLoanAmount(String grantedLoanAmount) {
        this.grantedLoanAmount = grantedLoanAmount;
    }

    /**
     *
     * @return
     * The onlinePayedAmount
     */
    public String getOnlinePayedAmount() {
        return onlinePayedAmount;
    }

    /**
     *
     * @param onlinePayedAmount
     * The onlinePayedAmount
     */
    public void setOnlinePayedAmount(String onlinePayedAmount) {
        this.onlinePayedAmount = onlinePayedAmount;
    }

    /**
     *
     * @return
     * The retailerReceived
     */
    public String getRetailerReceived() {
        return retailerReceived;
    }

    /**
     *
     * @param retailerReceived
     * The retailerReceived
     */
    public void setRetailerReceived(String retailerReceived) {
        this.retailerReceived = retailerReceived;
    }

    /**
     *
     * @return
     * The orderState
     */
    public String getOrderState() {
        return orderState;
    }

    /**
     *
     * @param orderState
     * The orderState
     */
    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    /**
     *
     * @return
     * The paymentType
     */
    public Integer getPaymentType() {
        return paymentType;
    }

    /**
     *
     * @param paymentType
     * The paymentType
     */
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

}
