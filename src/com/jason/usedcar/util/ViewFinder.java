package com.jason.usedcar.util;

import android.app.Activity;
import android.view.View;

/**
 * @author t77yq @14-6-27.
 */
public final class ViewFinder {

    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }
}
