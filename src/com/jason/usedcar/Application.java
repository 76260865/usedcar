package com.jason.usedcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import com.jason.usedcar.response.LoginResponse;
import com.jason.usedcar.service.InitService;
import com.jason.usedcar.util.AccessTokenUtil;
import java.security.NoSuchAlgorithmException;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

public class Application extends com.activeandroid.app.Application {

    public static final String TAG = "UsedCarApplication";

    public static String sampleAccessToken;

    private LoginResponse loginResponse;

    private BinderFactory reusableBinderFactory
            = new BinderFactoryBuilder().build();

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, InitService.class));
    }

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

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public String getAccessToken() {
        return loginResponse == null ? null : loginResponse.getAccessToken();
    }

    public BinderFactory getReusableBinderFactory() {
        return reusableBinderFactory;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(getAccessToken());
    }

    public boolean isReseller() {
        return loginResponse != null && loginResponse.getAccountType() == 2;
    }

    public String getUsername() {
        return loginResponse == null ? "" : loginResponse.getUsername();
    }

    public String getPassword() {
        return loginResponse == null ? "" : loginResponse.getPassword();
    }
}
