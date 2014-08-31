package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */
public class UpgradeRequest extends Request {

    private int version;

    private String message;

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
