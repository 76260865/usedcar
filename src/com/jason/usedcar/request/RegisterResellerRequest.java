package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */

public class RegisterResellerRequest extends RegisterRequest {

    private String resellerName;

    private int resellerType;

    private String address;

    public String getResellerName() {
        return resellerName;
    }

    public void setResellerName(final String resellerName) {
        this.resellerName = resellerName;
    }

    public int getResellerType() {
        return resellerType;
    }

    public void setResellerType(final int resellerType) {
        this.resellerType = resellerType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }
}
