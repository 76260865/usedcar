package com.jason.usedcar.pm.view;

import com.jason.usedcar.model.data.Order;

/**
 * @author t77yq @2014-11-12.
 */
public interface ViewConfirmShopping extends ViewBase {

    void confirmOrder(Order order);
}
