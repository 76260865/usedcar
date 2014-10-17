package com.jason.usedcar.presentation_model;

import android.content.Context;

/**
 * @author t77yq @2014-09-18.
 */
public interface ViewBase {

    Context getContext();

    boolean isLogin();

    void login();

    void login(int requestCode);

    String getAccessToken();

    void finish();
}
