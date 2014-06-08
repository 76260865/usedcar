package com.jason.usedcar;

import java.security.NoSuchAlgorithmException;

import android.app.Application;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;

import com.jason.usedcar.util.AccessTokenUtil;

public class UsedCarApplication extends Application {
    public static final String TAG = "UsedCarApplication";

    public String accessToken;

    public int userId;

    public String getEncryptedToken() {
        try {
            String MD5EncodeString = AccessTokenUtil.MD5Encode(accessToken
                    + AccessTokenUtil.mPrivateKey + SystemClock.currentThreadTimeMillis());
            String encryptString = MD5EncodeString + userId + "&"
                    + SystemClock.currentThreadTimeMillis();
            return Base64.encodeToString(encryptString.getBytes(), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
