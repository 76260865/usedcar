package com.jason.usedcar.response;

import com.jason.usedcar.model.data.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-11-01.
 */
public class OrderListResponse extends Response {

    private List<Order> orders = new ArrayList<Order>();
    private int totalOrders;

    /**
     *
     * @return
     * The orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     *
     * @param orders
     * The orders
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     *
     * @return
     * The totalOrders
     */
    public int getTotalOrders() {
        return totalOrders;
    }

    /**
     *
     * @param totalOrders
     * The totalOrders
     */
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

}
