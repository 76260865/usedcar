package com.jason.usedcar.pm;

import org.robobinding.annotation.PresentationModel;

/**
 * @author t77yq @2014-11-11.
 */
@PresentationModel
public abstract class SecurityCodePM {

    private String securityCode;

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        validate();
    }

    public abstract Boolean isRequestSecurityCodeEnabled();

    public abstract void requestSecurityCode();

    protected abstract void validate();
}
