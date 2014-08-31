package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */
public class Upgrade2Request extends Request {

    private String citySyncLastUpdate;

    private String carSyncLastUpdate;

    private String message;

    public String getCitySyncLastUpdate() {
        return citySyncLastUpdate;
    }

    public void setCitySyncLastUpdate(final String citySyncLastUpdate) {
        this.citySyncLastUpdate = citySyncLastUpdate;
    }

    public String getCarSyncLastUpdate() {
        return carSyncLastUpdate;
    }

    public void setCarSyncLastUpdate(final String carSyncLastUpdate) {
        this.carSyncLastUpdate = carSyncLastUpdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
