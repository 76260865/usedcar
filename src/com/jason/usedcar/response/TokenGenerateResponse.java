package com.jason.usedcar.response;

/**
 * @author t77yq @2014-08-18.
 */
public class TokenGenerateResponse extends Response {

    private String sampleAccessToken;

    public String getSampleAccessToken() {
        return sampleAccessToken;
    }

    public void setSampleAccessToken(final String sampleAccessToken) {
        this.sampleAccessToken = sampleAccessToken;
    }
}
