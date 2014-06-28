package com.jason.usedcar.presenter;

import android.content.Context;
import android.content.Intent;
import com.jason.usedcar.SellCarActivity;
import com.jason.usedcar.interfaces.Ui;

/**
 * @author t77yq @14-6-22.
 */
public class SellCarFragmentPresenter extends BasePresenter<Ui> {

    public void addUsedCar(Context context) {
        context.startActivity(new Intent(context, SellCarActivity.class));
    }

    public void editUsedCar() {
    }
}
