package com.jason.usedcar.pm.footer;

import android.view.View;
import com.jason.usedcar.model.ShoppingCarModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-11-09.
 */
@PresentationModel
public class ShoppingFooterPM {

    private ShoppingCarModel model;

    private PresentationModelChangeSupport changeSupport;

    public ShoppingFooterPM(ShoppingCarModel model) {
        this.model = model;
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    public int getFull() {
        return model.hasMore() ? View.GONE : View.VISIBLE;
    }

    public int getNotFull() {
        return model.hasMore() ? View.VISIBLE : View.GONE;
    }

    public void refresh() {
        changeSupport.refreshPresentationModel();
    }

}
