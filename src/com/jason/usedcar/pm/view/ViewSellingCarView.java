package com.jason.usedcar.pm.view;

import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-17.
 */
public interface ViewSellingCarView extends ViewBase {

    void viewProductDetails(Product product);

    void addNewCar();

    void delete(Product product);
}
