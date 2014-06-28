package com.jason.usedcar.model.result;

import com.jason.usedcar.model.ShoppingCar;
import lombok.Data;

/**
 * @author t77yq @14-6-28.
 */
@Data
public class ShoppingCarResult extends Result {

    private ShoppingCar[] usedCars;
}
