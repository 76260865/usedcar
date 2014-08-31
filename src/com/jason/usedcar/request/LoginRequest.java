package com.jason.usedcar.request;

@SuppressWarnings("unchecked")
public class LoginRequest extends Request {

    private String phoneOrEmail;

    private String password;

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public void setPhoneOrEmail(final String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
