package com.jason.usedcar.presentation_model;

import org.robobinding.aspects.PresentationModel;

/**
 * @author t77yq @2014-10-03.
 */
@PresentationModel
public class MenuSellCarViewModel {

    private ViewSellingCarView view;

    public MenuSellCarViewModel(ViewSellingCarView view) {
        this.view = view;
    }

    public void addNewCar() {
        view.addNewCar();
    }

    public void editCar() {
        //
    }
}
