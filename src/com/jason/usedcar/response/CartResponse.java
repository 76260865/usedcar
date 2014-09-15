package com.jason.usedcar.response;

import com.jason.usedcar.model.data.Product;

import java.util.List;

/**
 * @author t77yq @2014-09-13.
 */
public class CartResponse extends Response {

    private List<Product> cartList;

    public List<Product> getCartList() {
        return cartList;
    }

    public void setCartList(List<Product> cartList) {
        this.cartList = cartList;
    }
}
