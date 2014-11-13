package com.jason.usedcar.pm.view;

import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-29.
 */
public interface ShoppingCarView extends ViewBase {

    void viewProductDetails(Product product);

    void delete(Product product);

    void check(Product product);

    void showMessage(String msg);

    void showMessage(int msgId);

    void confirmShopping(Product product);
}
