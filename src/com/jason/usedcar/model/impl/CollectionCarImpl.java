package com.jason.usedcar.model.impl;

import com.jason.usedcar.model.CollectionCar;
import com.jason.usedcar.model.data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-09-17.
 */
public class CollectionCarImpl implements CollectionCar {

    private final List<Product> _productList = new ArrayList<Product>();

    @Override
    public Product getByIndex(int position) {
        return _productList.get(position);
    }

    @Override
    public List<Product> getAll() {
        return _productList;
    }

    @Override
    public void add(Product product) {
        _productList.add(product);
    }

    @Override
    public void addAll(List<Product> productList) {
        _productList.addAll(productList);
    }

    @Override
    public void clear() {
        _productList.clear();
    }

    @Override
    public boolean isEmpty() {
        return _productList.isEmpty();
    }
}
