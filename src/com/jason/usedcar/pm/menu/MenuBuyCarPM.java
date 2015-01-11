package com.jason.usedcar.pm.menu;

import com.jason.usedcar.pm.view.ViewBuyCarView;
import org.robobinding.annotation.PresentationModel;

/**
 * @author t77yq @2014-10-13.
 */
@PresentationModel
public class MenuBuyCarPM {

    private ViewBuyCarView view;

    public MenuBuyCarPM(ViewBuyCarView view) {
        this.view = view;
    }

    public void search() {
        view.search2("");
    }
}
