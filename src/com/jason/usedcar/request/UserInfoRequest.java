package com.jason.usedcar.request;

public class UserInfoRequest extends Request {

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

    private String nickname;

    private String realName;

    private int sex;

    private String birthyear;

    private String birthmonth;

    private String birthday;

    private String certificateNumber;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(String birthmonth) {
        this.birthmonth = birthmonth;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
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
