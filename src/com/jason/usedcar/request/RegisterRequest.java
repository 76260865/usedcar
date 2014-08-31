package com.jason.usedcar.request;

public class RegisterRequest extends Request {

    private String phone;

    private String phoneVerifyCode;

    private boolean acceptTerm;

    private String password;

    private String confirmPassword;

    private int accountType;

    public String getPhone() {
        return phone;
    }

    public <T extends Request> T setPhone(String phone) {
        this.phone = phone;
        return (T) this;
    }

    public String getPhoneVerifyCode() {
        return phoneVerifyCode;
    }

    public <T extends Request> T setPhoneVerifyCode(String phoneVerifyCode) {
        this.phoneVerifyCode = phoneVerifyCode;
        return (T) this;
    }

    public boolean isAcceptTerm() {
        return acceptTerm;
    }

    public <T extends Request> T setAcceptTerm(boolean acceptTerm) {
        this.acceptTerm = acceptTerm;
        return (T) this;
    }

    public String getPassword() {
        return password;
    }

    public <T extends Request> T setPassword(String password) {
        this.password = password;
        return (T) this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public <T extends Request> T setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return (T) this;
    }

    public int getAccountType() {
        return accountType;
    }

    public <T extends Request> T setAccountType(int accountType) {
        this.accountType = accountType;
        return (T) this;
    }
}
