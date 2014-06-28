package com.jason.usedcar;

import android.app.Application;
import android.util.Base64;
import android.util.Log;
import com.jason.usedcar.util.AccessTokenUtil;
import java.security.NoSuchAlgorithmException;

public class UsedCarApplication extends Application {

    public static final String TAG = "UsedCarApplication";

    public String accessToken;

    public int userId;

    public String getEncryptedToken(int userId, String accessToken) {
        try {
            String MD5EncodeString = AccessTokenUtil.MD5Encode(accessToken
                    + AccessTokenUtil.mPrivateKey + System.currentTimeMillis());
            String encryptString = MD5EncodeString + userId + "&"
                    + System.currentTimeMillis();
            return Base64.encodeToString(encryptString.getBytes(), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
