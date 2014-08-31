package com.jason.usedcar.model;

import java.util.List;

/**
 * @author t77yq @14-6-28.
 */
public class ShoppingCarModel extends BaseModel {

    private List<UsedCar> data;

    public ShoppingCarModel() {}

    public void add(List<UsedCar> cars) {
        if (data == null) {
            this.data = cars;
        } else {
            this.data.addAll(cars);
        }
    }

    public List<UsedCar> getData() {
        return data;
    }

    public void setData(List<UsedCar> data) {
        this.data = data;
    }

    @Override
    public boolean isEmpty() {
        return data == null || data.size() == 0;
    }

    public int size() {
        return isEmpty() ? 0 : data.size();
    }

    public UsedCar get(int position) {
        if (isEmpty() || position < 0 || position >= size()) {
            return null;
        }
        return data.get(position);
    }
}
