package com.jason.usedcar.pm.view;

import com.jason.usedcar.model.data.Product;

/**
 * @author t77yq @2014-09-17.
 */
public interface ViewCollectionView extends ViewBase {

    void viewCollectionItem(Product product);

    void edit();

    void save();

    boolean isEditable();

    void delete(Product product);

    void start();

    void end();
}
