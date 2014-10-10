package com.jason.usedcar.presentation_model;

import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-29.
 */
public interface CarDetailsView extends ViewBase {

    void callCarOwner(String phoneNumber);

    void openCalculator(Double carPrice);

    void before();

    void after();

    Product getProduct();
}
