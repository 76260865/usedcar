package com.jason.usedcar.pm.item;

import android.view.View;
import com.jason.usedcar.pm.view.ViewSellingCarView;
import org.robobinding.annotation.PresentationModel;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class CarItemPM2 extends CarItemPM {

    public CarItemPM2(final ViewSellingCarView view) {
        super(view);
    }

    public int getPayTypeVisibility() {
        return View.VISIBLE;
    }


    public int getCheckVisibility() {
        return View.GONE;
    }

    public boolean isChecked() {
        return false;
    }

    public void check() {

    }
}
