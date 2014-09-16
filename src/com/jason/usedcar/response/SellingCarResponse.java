package com.jason.usedcar.response;

import com.jason.usedcar.model.data.Product;

import java.util.List;

/**
 * @author t77yq @2014-09-13.
 */
public class SellingCarResponse extends Response {

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> sellingCars) {
        this.productList = sellingCars;
    }
}
