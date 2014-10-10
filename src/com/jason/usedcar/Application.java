package com.jason.usedcar;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import com.jason.usedcar.util.AccessTokenUtil;
import java.security.NoSuchAlgorithmException;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

public class Application extends com.activeandroid.app.Application {

    public static final String TAG = "UsedCarApplication";

    private String accessToken;

    public int userId;

    public static String sampleAccessToken;

    public String username;

    public String password;

    private BinderFactory reusableBinderFactory = new BinderFactoryBuilder().build();

    public static Application fromContext(Context context) {
        if (context instanceof Activity) {
            return fromActivity((Activity) context);
        }
        return (Application) context;
    }

    public static Application fromActivity(Activity activity) {
        return (Application) activity.getApplication();
    }

    public static String getEncryptedToken(int userId, String accessToken) {
        try {
            long currentTime = System.currentTimeMillis();
            System.out.println("(" + accessToken + " uja6snx21b " + currentTime + ")"
            + " " + userId + " & " + currentTime);
            return Base64.encodeToString(
                (AccessTokenUtil.MD5Encode(accessToken + "uja6snx21b" + currentTime)
                    + userId + "&" + currentTime ).getBytes(), Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            //
        }
        return null;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public BinderFactory getReusableBinderFactory() {
        return reusableBinderFactory;
    }
}
