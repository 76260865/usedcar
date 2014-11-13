package com.jason.usedcar.pm.footer;

import android.view.View;
import com.jason.usedcar.model.SaleCarModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-11-09.
 */
@PresentationModel
public class BuyFooterPM {

    private SaleCarModel model;

    private PresentationModelChangeSupport changeSupport;

    public BuyFooterPM(SaleCarModel model) {
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
