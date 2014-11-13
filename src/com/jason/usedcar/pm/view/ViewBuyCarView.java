package com.jason.usedcar.pm.view;

import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-17.
 */
public interface ViewBuyCarView {

    void fillFilter();

    void search(String queryStr);

    void search2(String where);

    void viewProductDetails(Product product);

    void showMessage(int messageId);

    void showMessage(String message);

    boolean isFull();
}
