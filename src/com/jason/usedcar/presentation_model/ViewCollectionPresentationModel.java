package com.jason.usedcar.presentation_model;

import com.jason.usedcar.model.CollectionCar;
import com.jason.usedcar.model.data.Product;
import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.aspects.PresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author t77yq @2014-09-17.
 */
@PresentationModel
public class ViewCollectionPresentationModel extends AbstractPresentationModel {

    private final ViewCollectionView view;

    private final CollectionCar car;

    private final PresentationModelChangeSupport changeSupport;

    public ViewCollectionPresentationModel(ViewCollectionView view, CollectionCar car) {
        this.view = view;
        this.car = car;
        this.changeSupport = new PresentationModelChangeSupport(this);
    }

    @ItemPresentationModel(CollectionItemPresentationModel.class)
    public List<Product> getProducts() {
        return new ArrayList<Product>(car.getAll());
    }

    public void refreshCollection() {
        changeSupport.firePropertyChange("products");
    }

    public void viewCollectionItem(ItemClickEvent event) {
        viewCollectionItem(event.getPosition());
    }

    public void viewCollectionItem(int selectedPosition) {
        view.viewCollectionItem(car.getByIndex(selectedPosition));
    }
}
