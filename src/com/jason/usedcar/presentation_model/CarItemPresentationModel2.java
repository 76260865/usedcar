package com.jason.usedcar.presentation_model;

import android.view.View;
import com.jason.usedcar.model.data.Product;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;

/**
 * @author t77yq @2014-09-17.
 */
public class CarItemPresentationModel2 extends CarItemPresentationModel {

    public int getPayTypeVisibility() {
        return View.VISIBLE;
    }
}
