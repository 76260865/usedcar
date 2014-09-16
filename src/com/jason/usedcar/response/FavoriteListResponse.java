package com.jason.usedcar.response;

import com.jason.usedcar.model.data.Product;

import java.util.List;

/**
 * @author t77yq @2014-09-16.
 */
public class FavoriteListResponse extends Response {

    private List<Product> favoriteList;

    public List<Product> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Product> favoriteList) {
        this.favoriteList = favoriteList;
    }
}

