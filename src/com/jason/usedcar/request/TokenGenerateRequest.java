package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-18.
 */
public class TokenGenerateRequest extends Request {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
