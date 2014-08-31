package com.jason.usedcar.request;

public class UserInfoRequest extends Request {

    private String nickname;

    private String realName;

    private String email;

    private boolean bindEmail;

    private boolean sex;

    private String phone;

    private boolean bindPhone;

    private String birthYear;

    private String birthMonth;

    private String birthDay;

    private String certificateNumber;

    private String province;

    private String city;

    private String county;

    private String street;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(final String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public boolean isBindEmail() {
        return bindEmail;
    }

    public void setBindEmail(final boolean bindEmail) {
        this.bindEmail = bindEmail;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(final boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public boolean isBindPhone() {
        return bindPhone;
    }

    public void setBindPhone(final boolean bindPhone) {
        this.bindPhone = bindPhone;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(final String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(final String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(final String birthDay) {
        this.birthDay = birthDay;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(final String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(final String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(final String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }
}
