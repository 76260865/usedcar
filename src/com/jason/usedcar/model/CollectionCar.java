package com.jason.usedcar.model;

import com.jason.usedcar.model.data.Product;

import java.util.List;

/**
 * @author t77yq @2014-09-17.
 */
public interface CollectionCar {

    Product getByIndex(int position);

    List<Product> getAll();

    void add(Product product);

    void addAll(List<Product> productList);

    void clear();

    boolean isEmpty();

    public void removeById(String id);
}
