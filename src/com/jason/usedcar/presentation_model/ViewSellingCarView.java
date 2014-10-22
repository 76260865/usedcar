package com.jason.usedcar.presentation_model;

import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-17.
 */
public interface ViewSellingCarView extends ViewBase {

    void viewProductDetails(Product product);

    void addNewCar();
}
