package com.jason.usedcar.pm.view;

/**
 * @author t77yq @2014-09-29.
 */
public interface CarDetailsView extends ViewBase {

    void callCarOwner(String phoneNumber);

    void openCalculator(double carPrice);

    void before();

    void after();

    String getProductId();

    void editCar();
}
