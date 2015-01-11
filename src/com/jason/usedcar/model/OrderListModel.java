package com.jason.usedcar.model;

import com.jason.usedcar.model.data.Order;
import com.jason.usedcar.model.data.Product;
import java.util.List;

/**
 * @author t77yq @2014.06.14
 */
public class OrderListModel extends BaseModel {

    public static final int PAGE_SIZE = 20;

    private boolean loading = false;

    private boolean hasMore = false;

    private List<Order> data;

    public OrderListModel() {}

    public OrderListModel(List<Order> data) {
        this.data = data;
    }

    public void add(List<Order> newData) {
        if (data == null) {
            this.data = newData;
        } else {
            this.data.addAll(newData);
        }
    }

    public  void clearAll() {
        this.data.clear();
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order> data) {
        this.data = data;
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public Order get(int position) {
        return (position < 0 || position >= size()) ? null : data.get(position);
    }

    public boolean hasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPageSize() {
        return  isEmpty() ? 0 : data.size() / PAGE_SIZE;
    }
}
