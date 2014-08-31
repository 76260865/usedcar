package com.jason.usedcar;

import android.os.Build;
import android.os.Build.VERSION;

/**
 * @author t77yq @2014.06.13
 */
public final class Config {

    public static final String USER_AGENT = "UserCar/" + "1.0" + " (" + Build.MANUFACTURER
        + " " + Build.DEVICE + "; Android " + VERSION.RELEASE + "; Scale/2.00)";

    private Config() {}
}
