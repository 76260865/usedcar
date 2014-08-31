package com.jason.usedcar.response;

/**
 * @author t77yq @2014-08-02.
 */
public class Upgrade2Response extends Response {

    private boolean citySync;

    private boolean carSync;

    public boolean isCitySync() {
        return citySync;
    }

    public void setCitySync(boolean citySync) {
        this.citySync = citySync;
    }

    public boolean isCarSync() {
        return carSync;
    }

    public void setCarSync(boolean carSync) {
        this.carSync = carSync;
    }
}
