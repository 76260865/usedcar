package com.jason.usedcar.response;

import com.jason.usedcar.model.data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq  by t77yq on 14-9-12.
 */
public class SearchProductResponse extends Response {

    private List<Product> productList = new ArrayList<Product>();

    private Integer numFound;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> products) {
        this.productList = products;
    }

    public Integer getNumFound() {
        return numFound;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

}
