package com.jason.usedcar.request;

public class ResetPasswordByPhoneRequest extends Request {

    private String principle;

    private String activeCode;

    private String newPassword;

    private String confirmPassword;

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(final String principle) {
        this.principle = principle;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(final String activeCode) {
        this.activeCode = activeCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
