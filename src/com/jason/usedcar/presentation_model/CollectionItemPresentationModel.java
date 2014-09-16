package com.jason.usedcar.presentation_model;

import com.jason.usedcar.model.data.Product;
import org.robobinding.itempresentationmodel.AbstractItemPresentationModel;

/**
 * @author t77yq @2014-09-17.
 */
public class CollectionItemPresentationModel extends AbstractItemPresentationModel<Product> {

    protected Product product;

    public String getTitle() {
        return product.getProductName();
    }

    public String getPrice() {
        return product.getPrice();
    }

    @Override
    protected void doUpdateData(int i, Product product) {
        this.product = product;
    }
}
