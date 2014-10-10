package com.jason.usedcar.model;

import com.jason.usedcar.model.data.Product;

import java.util.List;

/**
 * @author t77yq @14-6-28.
 */
public class ShoppingCarModel extends BaseModel {

    private List<Product> data;

    private boolean loading;

    private boolean hasMore;

    public ShoppingCarModel() {}

    public void add(List<Product> cars) {
        if (data == null) {
            this.data = cars;
        } else {
            this.data.addAll(cars);
        }
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.size() == 0;
    }

    public int size() {
        return isEmpty() ? 0 : data.size();
    }

    public Product get(int position) {
        if (isEmpty() || position < 0 || position >= size()) {
            return null;
        }
        return data.get(position);
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean hasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
