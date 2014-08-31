package com.jason.usedcar.request;

public class ForgetPasswordRequest extends Request {

    private String loginName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }
}
