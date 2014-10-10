package com.jason.usedcar.presentation_model;

import android.view.View;
import com.jason.usedcar.model.data.SProduct;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.widget.view.ClickEvent;

/**
 * @author t77yq @2014-09-17.
 */
public class CollectionItemPresentationModel extends AbstractPresentationModel implements ItemPresentationModel<SProduct> {

    protected SProduct product;

    private ViewCollectionView view;

    public CollectionItemPresentationModel(final ViewCollectionView view) {
        this.view = view;
    }

    public String getTitle() {
        return product.product.getProductName();
    }

    public String getPrice() {
        return product.product.getPrice();
    }

    public int getVisibility() {
        return product.isEditable ? View.VISIBLE : View.GONE;
    }

    public void delete(ClickEvent event) {
        //
        view.delete(product.product);
    }

    @Override
    public void updateData(final int i, final SProduct product) {
        this.product = product;
    }
}
