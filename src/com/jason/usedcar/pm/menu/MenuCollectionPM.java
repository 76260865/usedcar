package com.jason.usedcar.pm.menu;

import com.jason.usedcar.pm.view.ViewCollectionView;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

/**
 * @author t77yq @2014-09-20.
 */
@PresentationModel
public class MenuCollectionPM {

    private ViewCollectionView view;

    private PresentationModelChangeSupport support;

    public MenuCollectionPM(ViewCollectionView view) {
        this.view = view;
        this.support = new PresentationModelChangeSupport(this);
    }

    public boolean isEditable() {
        return view.isEditable();
    }

    public boolean isNotEditable() {
        return !isEditable();
    }

    public void edit() {
        view.edit();
    }

    public void save() {
        view.save();
    }

    public void refresh() {
        support.refreshPresentationModel();
    }
}
