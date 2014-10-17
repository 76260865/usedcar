package com.jason.usedcar.presentation_model;

import org.robobinding.aspects.PresentationModel;

/**
 * @author t77yq @2014-10-13.
 */
@PresentationModel
public class MenuBuyCarViewModel {

    private ViewBuyCarView view;

    public MenuBuyCarViewModel(ViewBuyCarView view) {
        this.view = view;
    }

    public void search() {
        view.search2("");
    }
}
