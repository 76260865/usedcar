package com.jason.usedcar.pm.item;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * @author t77yq @2014-11-02.
 */
@PresentationModel
public class SpinnerItemViewModel implements ItemPresentationModel<String> {

    private String id;

    public String getId() {
        return id;
    }

    public void updateData(int i, String s) {
        id = s;
    }

    @Override
    public void updateData(String s, ItemContext context) {
        id = s;
    }
}
