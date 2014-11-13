package com.jason.usedcar.pm.view;

/**
 * @author t77yq @2014-10-27.
 */
public interface CarBaseInfoView extends ViewBase {

    void message(int messageId);

    void message(String message);

    void pickPlace();

    void pickDate();

    void pickCar();
}
