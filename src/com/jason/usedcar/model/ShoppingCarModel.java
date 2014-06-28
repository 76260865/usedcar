package com.jason.usedcar.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author t77yq @14-6-28.
 */
public class ShoppingCarModel extends BaseModel {

    @Getter @Setter ShoppingCar[] data;

    public ShoppingCarModel() {}

    public ShoppingCarModel(ShoppingCar[] data) {
        this.data = data;
    }

    public void add(ShoppingCar[] cars) {
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.length == 0;
    }

    public int size() {
        return isEmpty() ? 0 : data.length;
    }

    public ShoppingCar get(int position) {
        if (isEmpty() || position < 0 || position >= size()) {
            return null;
        }
        return data[position];
    }
}
