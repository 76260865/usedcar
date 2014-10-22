package com.jason.usedcar.presentation_model;

import android.view.View;
import com.jason.usedcar.model.data.Product;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;

/**
 * @author t77yq @2014-09-17.
 */
public class CarItemPresentationModel extends AbstractPresentationModel implements ItemPresentationModel<Product> {

    private Product product;

    public CarItemPresentationModel() {
    }

    public String getName() {
        return product.getProductName();
    }

    public String getPrice() {
        return product.getPrice();
    }

    public String getOdometer() {
        return product.getOdometer();
    }

    public String getPurchaseDate() {
        return product.getPurchaseDate();
    }

    public String getPayType() {
        return "";
    }

    public int getPayTypeVisibility() {
        return View.VISIBLE;
    }

    @Override
    public void updateData(final int i, final Product product) {
        this.product = product;
    }
}
