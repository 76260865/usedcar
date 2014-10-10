package com.jason.usedcar.model.impl;

import com.jason.usedcar.model.CollectionCar;
import com.jason.usedcar.model.data.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author t77yq @2014-09-17.
 */
public class CollectionCarImpl implements CollectionCar {

    private final List<Product> _productList = new ArrayList<Product>();

    private final Map<String, Product> productIndex = new HashMap<String, Product>();

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
        productIndex.put(product.getProductId(), product);
    }

    @Override
    public void addAll(List<Product> productList) {
        _productList.addAll(productList);
        for (Product product : productList) {
            productIndex.put(product.getProductId(), product);
        }
    }

    @Override
    public void clear() {
        _productList.clear();
        productIndex.clear();
    }

    @Override
    public boolean isEmpty() {
        return _productList.isEmpty();
    }

    public void removeById(String productId) {
        _productList.remove(productIndex.get(productId));
    }
}
