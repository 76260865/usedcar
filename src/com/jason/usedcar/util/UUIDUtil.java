package com.jason.usedcar.util;

import java.util.UUID;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

public class UUIDUtil {
    private static final String TAG = "UUIDUtil";

    public static String getUUID(SharedPreferences sharePrefs) {
        String uuid = "";
        if (sharePrefs != null) {
            uuid = sharePrefs.getString("uuid", "");
        }

        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            Editor enditor = sharePrefs.edit();
            enditor.putString("uuid", uuid);
            enditor.commit();
        }

        Log.d(TAG, "getUUID : " + uuid);
        return uuid;
    }
}
