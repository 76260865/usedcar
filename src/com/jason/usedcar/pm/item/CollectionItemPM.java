package com.jason.usedcar.pm.item;

import android.view.View;
import com.jason.usedcar.model.data.Product;
import com.jason.usedcar.pm.view.ViewCollectionView;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.widget.view.ClickEvent;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class CollectionItemPM implements ItemPresentationModel<Product> {

    protected Product product;

    private ViewCollectionView view;

    public CollectionItemPM(final ViewCollectionView view) {
        this.view = view;
    }

    public String getTitle() {
        return product.getProductName();
    }

    public String getPrice() {
        return product.getPrice();
    }

    public int getVisibility() {
        return product.isMark() ? View.VISIBLE : View.GONE;
    }

    public void delete(ClickEvent event) {
        //
        view.delete(product);
    }

    public void updateData(int i, Product product) {
        this.product = product;
    }

    @Override
    public void updateData(Product product, ItemContext context) {
        this.product = product;
    }
}
