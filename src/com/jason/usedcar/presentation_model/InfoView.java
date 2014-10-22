package com.jason.usedcar.presentation_model;

/**
 * @author t77yq @2014-09-29.
 */
public interface InfoView extends ViewBase {

    void changePassword(String phoneNumber);

    void changePhone(String phoneNumber);

    void pickTime();

    void start();

    void stop();

    void tell(String msg);
}
