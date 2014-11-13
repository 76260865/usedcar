package com.jason.usedcar.pm.view;

import android.content.Context;
import android.content.Intent;

/**
 * @author t77yq @2014-09-18.
 */
public interface ViewBase {

    Context getContext();

    String getAccessToken();

    boolean isLogin();

    void login();

    void login(int requestCode);

    void finish();

    void finish(int resultCode);

    void finish(int resultCode, Intent data);

    void showProgress();

    void showProgress(int messageId);

    void showProgress(String message);

    void dismissProgress();

    void showMessage(int messageId);

    void showMessage(String message);
}
