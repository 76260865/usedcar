package com.jason.usedcar.adapter.holder;

import android.view.*;

/**
 * @author t77yq @2014.06.14
 */

@SuppressWarnings("unchecked")
public class ViewHolder {

    protected <T extends View> T getView(View view, int id) {
        return (T) view.findViewById(id);
    }
}
