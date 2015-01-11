package com.jason.usedcar.pm.view;

/**
 * @author t77yq @2014-11-08.
 */
public interface OrderConfirmView extends ViewBase {

    void before();

    void end();

    void showMessage(String message);
}
