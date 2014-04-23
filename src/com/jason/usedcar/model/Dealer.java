package com.jason.usedcar.model;

public class Dealer {
    private String phone;
    private String phoneVerifyCode;
    private String resellerName;
    private int resellerType;
    private String adress;
    private String nickname;
    private String email;
    private boolean acceptTerm;
    private String password;
    private String repassword;
    private int accountType;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneVerifyCode() {
        return phoneVerifyCode;
    }

    public void setPhoneVerifyCode(String phoneVerifyCode) {
        this.phoneVerifyCode = phoneVerifyCode;
    }

    public String getResellerName() {
        return resellerName;
    }

    public void setResellerName(String resellerName) {
        this.resellerName = resellerName;
    }

    public int getResellerType() {
        return resellerType;
    }

    public void setResellerType(int resellerType) {
        this.resellerType = resellerType;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

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

    public boolean isAcceptTerm() {
        return acceptTerm;
    }

    public void setAcceptTerm(boolean acceptTerm) {
        this.acceptTerm = acceptTerm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

}
