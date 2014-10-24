package com.jason.usedcar.response;

import java.io.Serializable;

/**
 * @author t77yq @2014-09-29.
 */
public class LicenseImage implements Serializable {

    private Integer imageId;
    private String description;
    private String sizeJumbo;
    private String sizeLarge;
    private String sizeRegular;
    private String sizeSmall;
    private String sizeThumbnail;
    private String type;
    private String updateTime;
    private String name;
    private Object productId;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSizeJumbo() {
        return sizeJumbo;
    }

    public void setSizeJumbo(String sizeJumbo) {
        this.sizeJumbo = sizeJumbo;
    }

    public String getSizeLarge() {
        return sizeLarge;
    }

    public void setSizeLarge(String sizeLarge) {
        this.sizeLarge = sizeLarge;
    }

    public String getSizeRegular() {
        return sizeRegular;
    }

    public void setSizeRegular(String sizeRegular) {
        this.sizeRegular = sizeRegular;
    }

    public String getSizeSmall() {
        return sizeSmall;
    }

    public void setSizeSmall(String sizeSmall) {
        this.sizeSmall = sizeSmall;
    }

    public String getSizeThumbnail() {
        return sizeThumbnail;
    }

    public void setSizeThumbnail(String sizeThumbnail) {
        this.sizeThumbnail = sizeThumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProductId() {
        return productId;
    }

    public void setProductId(Object productId) {
        this.productId = productId;
    }

}
