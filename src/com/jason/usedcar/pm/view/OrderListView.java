package com.jason.usedcar.pm.view;

import com.jason.usedcar.model.data.Order;

/**
 * @author t77yq @2014-11-02.
 */
public interface OrderListView extends ViewBase {

    void confirmOrder(Order order);
}
