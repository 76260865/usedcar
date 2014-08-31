package com.jason.usedcar;

import android.content.Context;
import android.widget.Toast;

/**
 * @author t77yq @2014-08-21.
 */
public class MessageToast {

    public static Toast makeText(Context context, int messageId) {
        return makeText(context, context.getString(messageId));
    }

    public static Toast makeText(Context context, String message) {
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }
}
