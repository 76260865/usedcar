package com.jason.usedcar.pm.item;

import org.robobinding.annotation.PresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

/**
 * @author t77yq @2014-11-02.
 */
@PresentationModel
public class SpinnerItemViewModel2 implements ItemPresentationModel<String> {

    private String bank;

    public String getBank() {
        return bank;
    }

    public void updateData(int i, String s) {
        bank = s;
    }

    @Override
    public void updateData(String s, ItemContext context) {
        bank = s;
    }
}
