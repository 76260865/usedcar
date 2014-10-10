package com.jason.usedcar.presentation_model;

import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-17.
 */
public interface ViewBuyCarView {

    void fillFilter();

    void search();

    void viewProductDetails(Product product);

    void showMessage(int messageId);

    void showMessage(String message);

    boolean isFull();
}
