package com.jason.usedcar.model;

import com.jason.usedcar.model.data.Product;

import java.util.List;

/**
 * @author t77yq @2014.06.14
 */
public class SaleCarModel extends BaseModel {

    public static final int PAGE_SIZE = 20;

    private boolean loading = false;

    private boolean hasMore = false;

    private List<Product> data;

    public SaleCarModel() {}

    public SaleCarModel(List<Product> data) {
        this.data = data;
    }

    public void add(List<Product> newData) {
        if (data == null) {
            this.data = newData;
        } else {
            this.data.addAll(newData);
        }
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public int size() {
        return data == null ? 0 : data.size();
    }

    public Product get(int position) {
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
