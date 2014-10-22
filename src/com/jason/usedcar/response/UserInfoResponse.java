package com.jason.usedcar.response;

public class UserInfoResponse extends Response {

    private String resellerName;

    private int resellerType;

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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final String birthdate) {
        this.birthdate = birthdate;
    }

    private String nickname;

    private String email;

    private boolean bindEmail;

    private int sex;

    private String phone;

    private boolean bindPhone;

    private String birthdate;

    private String province;

    private String city;

    private String county;

    private String street;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBindEmail() {
        return bindEmail;
    }

    public void setBindEmail(boolean bindEmail) {
        this.bindEmail = bindEmail;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(boolean bindPhone) {
        this.bindPhone = bindPhone;
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
}
