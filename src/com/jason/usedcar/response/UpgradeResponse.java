package com.jason.usedcar.response;

/**
 * @author t77yq @2014-08-02.
 */
public class UpgradeResponse extends Response {

    private boolean upgrade;

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }
}
