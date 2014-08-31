package com.jason.usedcar.request;

/**
 * @author t77yq @2014-08-02.
 */
public class ShoppingCarOperationRequest extends CarRequest {

    private boolean addShoppingCar;

    public boolean isAddShoppingCar() {
        return addShoppingCar;
    }

    public void setAddShoppingCar(final boolean addShoppingCar) {
        this.addShoppingCar = addShoppingCar;
    }
}
