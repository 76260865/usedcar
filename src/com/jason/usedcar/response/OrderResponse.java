package com.jason.usedcar.response;

import com.jason.usedcar.model.data.Order;

/**
 * @author t77yq @2014-11-07.
 */
public class OrderResponse extends Response {

    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
