package com.jason.usedcar.response;

import com.jason.usedcar.model.UsedCar;
import java.util.List;

/**
 * @author t77yq @2014-08-02.
 */
public class CarListResponse extends Response {

    private List<UsedCar> usedCars;

    public List<UsedCar> getUsedCars() {
        return usedCars;
    }

    public void setUsedCars(List<UsedCar> usedCars) {
        this.usedCars = usedCars;
    }
}
