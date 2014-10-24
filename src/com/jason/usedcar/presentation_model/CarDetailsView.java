package com.jason.usedcar.presentation_model;

/**
 * @author t77yq @2014-09-29.
 */
public interface CarDetailsView extends ViewBase {

    void callCarOwner(String phoneNumber);

    void openCalculator(String carPrice);

    void before();

    void after();

    String getProductId();

    void editCar();
}
